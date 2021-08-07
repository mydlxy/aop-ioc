package com.myd.ioc.factory;

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

import java.io.IOException;
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
    public AnnotationBeanFactory(String scanPackage, XmlConfiguration xmlConfiguration) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
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
    public void registerBean(String scanPackage) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        Map<String,List<Field>> temp = new HashMap<>();
        List<String> classNames = RegexUtils.scanClassNames(scanPackage);
        if(classNames.isEmpty())return;
        for (String className : classNames) {
            Class beanClass = Class.forName(className);
            Object bean = beanClass.newInstance();//因为前面已经处理过bean，这里必须生成一个bean就处理一个
            bean = beanPostProcess(bean);
            Component component = (Component) beanClass.getAnnotation(Component.class);
            if(component == null)continue;
            String id = getComponentId(beanClass,component);
            List<Field> autowired = findFieldAnnotation(beanClass,Autowired.class);
            List<Field> values = findFieldAnnotation(beanClass, Value.class);
            injectValue(values,bean);
            container.registerBean(id,bean);
            if(!autowired.isEmpty())
                temp.put(id,autowired);
        }
        if(temp.isEmpty())return;
        injectAutowired(temp);
    }

    public Object beanPostProcess(Object target){
        if(!AspectConfig.hasAspectConfig())return target;
        try {
            BeanPostAfterInitProcessor beanPostAfterInitProcessor = BeanPostAfterInitProcessor.getBeanPostAfterInitProcessor();
            return beanPostAfterInitProcessor.postProcessAfterInitialization(target);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
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
     * 将解析的所有properties文件值合并
     *
     */
    public void mergeProperties(){
        Collection<Properties> values = propertiesMap.values();
        for (Properties value : values) {
//            propertiesValues.
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
    public void injectAutowired(Map<String,List<Field>> map) throws IllegalAccessException, ClassNotFoundException {
        for(Iterator<String> iterator = map.keySet().iterator();iterator.hasNext();){
            String id = iterator.next();
            Object bean = container.getBean(id);
            List<Field> fields = map.get(id);
            for (Field field : fields) {
                String typeName = field.getType().getTypeName();
//                log.debug(typeName);
                Object value = container.getBean(Class.forName(typeName));
                ReflectUtils.setValue(bean,field,value);
            }
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
     * 
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
