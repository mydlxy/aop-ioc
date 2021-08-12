package com.myd.ioc.factory;

import com.myd.aop.AopUtils;
import com.myd.aop.BeanPostAfterInitProcessor;
import com.myd.aop.config.AspectConfig;
import com.myd.ioc.annotations.*;
import com.myd.ioc.beans.IocContainer;
import com.myd.ioc.context.XmlConfiguration;
import com.myd.ioc.exception.PropertiesKeyNotFound;
import com.myd.ioc.exception.PropertiesKeyRepeatError;
import com.myd.ioc.exception.RefNotFoundError;
import com.myd.ioc.parse.ParseProperty;
import com.myd.ioc.utils.BeanUtils;
import com.myd.ioc.utils.ReflectUtils;
import com.myd.ioc.utils.RegexUtils;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author myd
 * @date 2021/7/31  0:14
 */

public class AnnotationBeanFactory {
    
    Logger log = Logger.getLogger(getClass());
    /*properties文件, Map<classPath,properties>*/
    private Map<String,Properties> propertiesMap = new HashMap<>();
    /*propertiesMap的 key-value*/
    private Map<String ,String> propertiesValues =  new HashMap<>();
    /*需要扫描的注解的package*/
    private String scanPackage;

    /*@Configuration注解的类，支持一个@Configuration注解*/
    private Class configClass;
    /*ioc容器*/
    private IocContainer container;
    //只有注解配置bean
    public AnnotationBeanFactory(Class configClass){
        this.configClass = configClass;
    }
    //从xml文件中，获取到扫描的路径；xml + 注解 ：混合的配置bean
    //解析优先级： 解析xml > 解析注解
    public AnnotationBeanFactory(String scanPackage, XmlConfiguration xmlConfiguration) throws IllegalAccessException, InstantiationException, ClassNotFoundException, NoSuchMethodException, NoSuchFieldException {
        this.scanPackage = scanPackage;
        if(xmlConfiguration != null)
            this.propertiesMap.put(xmlConfiguration.getPropertiesPath(), xmlConfiguration.getProperties());
        this.container = IocContainer.container();
        mergeProperties();//将propertiesMap 的值存放到 propertiesValues中；
        registerBean(scanPackage);

    }


    /**
     *
     *
     * 找到@Component注解的类，将其注释到 IocContainer 中
     * 支持@Autowired注解;
     * e.g.
     * @Component
     * public class Model{
     *     @Autowired
     *     private User user;
     * }
     *
     * @param scanPackage
     */
    public void registerBean(String scanPackage) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, NoSuchFieldException {

        Map<String,List<Field>> temp = new HashMap<>();
        List<String> classNames = RegexUtils.scanClassNames(scanPackage);
        if(classNames.isEmpty())return;
        Map<String,Object> scanBeans = new HashMap<>();
        for (String className : classNames) {
            Class beanClass = Class.forName(className);
            Component component = (Component) beanClass.getAnnotation(Component.class);
            if(component == null)continue;
            Object bean = beanClass.newInstance();
            String id = getComponentId(beanClass,component);
            List<Field> autowired = AopUtils.findFieldAnnotation(beanClass,Autowired.class);
            List<Field> values = AopUtils.findFieldAnnotation(beanClass, Value.class);
            injectValue(values,bean);
            scanBeans.put(id,bean);
            if(!autowired.isEmpty())
                temp.put(id,autowired);
        }


        if(scanBeans.isEmpty())return;
        if(AspectConfig.hasAspectConfig()){
            registerBeanAndAOP(scanBeans);
        }else{
            registerBean(scanBeans);
        }


        if(temp.isEmpty())return;
        injectAutowired(temp);



    }


    public void registerBean(Map<String,Object> scanBeans){
        for(Iterator<String> iter = scanBeans.keySet().iterator();iter.hasNext();){
            String id = iter.next();
            container.registerBean(id,scanBeans.get(id));
        }


    }



    /**
     *
     * jdk代理生成的对象，被代理之后注解不会丢失（因为动态代理生成的代理对象，会持有原生类对象）
     * 被JDK代理生成的类是Proxy的子类（被代理类的继承结构：BeanProxy  extends Proxy  implements Interface1,Interface2..）
     * 而原生类的对象被注入到父类Proxy的一个字段：h；
     * 因此jdk动态代理类，可以获取原生类的信息;
     * 原生类:target
     * 生成新对象：Proxy.newInstance(target.getClass(),target.getClass().getInterfaces(),this);
     * BeanProxy beanProxy = new BeanProxy();
     * beanProxy.super.h = target;
     *
     *
     *
     *
     * cglib代理生成的对象，会丢失注解；代理类是原生类的子类，而字段注解会丢失；因此需要对注解的字段重新赋值；
     * 原因：cglib代理的类会继承原生对象类，生成一个新对象；原生对象没有关联；
     *  class A{}
     * A target  = new A();
     * target对象被cglib动态代理会生成一个新类:
     * class A$$CGLIB$$ extends A{}
     * 此时会生成一个新的对象：
     * A$$CGLIB$$ cglibA  =new A$$CGLIB$$();
     * 对象：cglibA与target没有关联，
     *
     * 为什么只对@Value的注解赋值而不对@Autowired注解的字段赋值？
     *
     * 因为采取的aop策略是先将注册在iocContainer容器中所有的类，根据配置的pointcut匹配过滤，筛选出所有需要aop的类；
     * 将这些类全部动态代理之后，再来给被@Autowired注解的字段注入值，这样可以避免被autowired注解的字段注入的类被aop之后，还要再次更换值；
     * e.g.
     *
     * 现在容器中有 A,B两个对象
     * class A{
     *
     * @Autowired
     * B b;
     * }
     * 如果在A被cglib动态代理之后，理解注入 B对象；
     *
     * 而B对象也会被 cglib代理，这样在B被代理之后，还需要更新 A中B对象的值；这样做显得有些愚蠢；
     *
     * 因此采用先将 容器中所有要被 aop的类，全部aop之后再来注入被@Autowired注解字的段值
     *
     *
     *
     *
     * @param scanBeans
     *
     * @return
     */
    public void registerBeanAndAOP(Map<String,Object> scanBeans) throws IllegalAccessException, NoSuchMethodException {
        BeanPostAfterInitProcessor beanPostAfterInitProcessor = BeanPostAfterInitProcessor.getBeanPostAfterInitProcessor();
        for(Iterator<String> iter = scanBeans.keySet().iterator();iter.hasNext();) {
            String id = iter.next();
            Object target = scanBeans.get(id);
            Object postBean = beanPostAfterInitProcessor.postProcessAfterInitialization(target);
            List<Field> beanValue = AopUtils.getBeanValue(postBean.getClass());//获取@Value注解
            if (postBean.getClass().getName().matches("(\\w+\\.)*\\w+\\$\\$EnhancerByCGLIB\\$\\$.*") && !beanValue.isEmpty()) {
                injectValue(beanValue, postBean);//将@Value注解的值注入到字段中;
            }
            container.registerBean(id,postBean);
        }

    }



    
    
    public String getComponentId(Class beanClass,Component component){
        String value = component.value();
        if(value.equals("")){
            String simpleName = beanClass.getSimpleName();
            return simpleName.toLowerCase().charAt(0)+simpleName.substring(1);
        }
        return value;
    }

    /**
     * 
     * 给使用了@Value的Field注入值；
     *
     * 检测到'${}' ， '${}' -> properties文件中定义的值;
     *
     *
     * @param fields
     * 
     */
    public void injectValue( List<Field> fields,Object bean ) throws IllegalAccessException {
        for (Field field : fields) {
            String value = field.getAnnotation(Value.class).value().trim();
            if(value.matches("^(\\$\\{).+\\}$")){//  ${username} -> username
                String key = value.substring(2,value.length()-1);
                value = getPropertiesValue(key);
            }
            Object fieldValue =BeanUtils.typeConvert(value,field.getType().getSimpleName());
            ReflectUtils.setValue(bean,field,fieldValue);
        }
    }

    public String getPropertiesValue(String key){
        String value = propertiesValues.get(key);
        if(value!= null)return value;
        throw new PropertiesKeyNotFound(" property key :" +key+" not found in properties file");
    }

    /**
     *
     * 将解析的所有 properties 文件值合并
     *
     */
    public void mergeProperties(){
        Collection<Properties> values = propertiesMap.values();
        for (Properties value : values) {
            for(Iterator iter = value.keySet().iterator();iter.hasNext();){
                String next = (String)iter.next();
                if(propertiesValues.containsKey(next)){
                     throw new PropertiesKeyRepeatError("properties key repeat:"+next);
                }
                propertiesValues.put(next,(String)value.get(next));
            }

        }
        
    }
    
    
    
    /**
     *
     * 给使用了@Autowired注解的字段注入对象；
     *
     */
    public void injectAutowired(Map<String,List<Field>> map) throws IllegalAccessException, ClassNotFoundException, NoSuchFieldException {
        for(Iterator<String> iterator = map.keySet().iterator();iterator.hasNext();){
            String id = iterator.next();
            Object bean = container.getBean(id);
            List<Field> fields = map.get(id);
            AopUtils.injectAutowired(bean,fields);
        }
    }









    /**
     * 
     * 
     *查找class中，带有某个注解的字段
     * 
     * e.g.
     * 
     * public class User{
     *      @Autowired
     *     private Model model;
     *   
     *   @Autowired
     *   private Teacher teacher;
     *   
     *   private String name;
     * }
     * 
     * 要找User中所有带有@Autowired注解的字段
     * 没有aop可以这样找，有了aop就不行了；
     * findFieldAnnotation(User.class,Autowired.class)
     * ===> return model,teacher
     * 
     * 
     * 
     * @param bean
     * @param annotationClass
     * @return
     */
    public List<Field> findFieldAnnotation(Class bean,Class annotationClass){
        List<Field> fields = new ArrayList<>();
        Field[] allField = bean.getDeclaredFields();
        if(allField== null)return null;
        for (Field field : allField) {
            Annotation autowired = field.getAnnotation(annotationClass);
            if(autowired == null)continue;
            fields.add(field);
        }
        return fields;
    }





    /**
     *
     *根据properties路径找到properties文件，解析
     * @param propertiesPath
     */
    public void parseProperties(String[] propertiesPath) throws IOException {
        for (String path : propertiesPath) {
            if(propertiesMap.containsKey(path))continue;//去重，已经解析过的properties文件不再重复解析；
            Properties properties = ParseProperty.parseProperty(path);
            propertiesMap.put(path,properties);
        }
    }

    /**
     * 判断properties路径是否为null；
     * @param propertiesPath
     * @return
     */
    public boolean nullPath(String[] propertiesPath){
        return propertiesPath.length == 1 && propertiesPath[0].trim().length() == 0;
    }




    public <T>T getBean(String id){
        return (T)container.getBean(id);
    }


    public <T>T getBean(Class classType){
        return container.getBean(classType);
    }




}
