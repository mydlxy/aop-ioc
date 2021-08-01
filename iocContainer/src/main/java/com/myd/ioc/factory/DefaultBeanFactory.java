package com.myd.ioc.factory;

import com.myd.ioc.beans.BeanDefinition;
import com.myd.ioc.beans.IocContainer;
import com.myd.ioc.beans.PropertyValue;
import com.myd.ioc.context.XmlConfiguration;
import com.myd.ioc.exception.CircularDependencyError;
import com.myd.ioc.utils.BeanUtils;
import org.apache.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * @author myd
 * @date 2021/7/28  21:33
 */

public class DefaultBeanFactory implements BeanFactory {


    private Logger log = Logger.getLogger(getClass());
    /**
     * beanDefinition -> bean；
     * 创建bean有2个阶段：
     * 1.根据构造方法的参数列表，匹配构造方法创建bean
     * 2.给bean的属性赋值
     */
//   private Map<String,Object> beans = new HashMap<>(64);

    private IocContainer container;
    /***
     * 创建bean时，会将正在创建的bean的className放在队列中，用来检测循环依赖错误；
     */
   private List<String> currentCreateBean = new ArrayList<>();
   private XmlConfiguration xmlConfiguration;

   public DefaultBeanFactory(XmlConfiguration xmlConfiguration){
       this.xmlConfiguration = xmlConfiguration;
       this.container = IocContainer.container();
   }

   public Object getBean(String id){
       return container.getBean(id);
   }

   public <T>T getBean(Class beanClass){
       return container.getBean(beanClass);
   }
   public void createBeans(Collection<BeanDefinition> beanDefinitions) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
       for (BeanDefinition beanDefinition : beanDefinitions) {
           createBean(beanDefinition);
       }

       for (BeanDefinition beanDefinition : beanDefinitions) {
           Object bean = container.getBean(beanDefinition.getId());
           assembleBean(beanDefinition,bean);

       }

   }

    @Override
    public <T> T createBean(BeanDefinition beanDefinition) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        String id = beanDefinition.getId();
        /*创建过就直接返回,这个类是：创建xml中定义的bean；判断beanDefinition的id是否在container中，是否已经被解析生成了bean*/
        if(container.isRegister(id))return null;
        String className = beanDefinition.getClassName();
        if(currentCreateBean.contains(className)){
            throw new CircularDependencyError(className+" created bean circular dependency error...");
        }
        currentCreateBean.add(className);
        Class<?> beanClass = Class.forName(className);
        Object bean;
        if(!beanDefinition.getConstructorValues().isEmpty()){//带参数的构造方法
            Constructor constructor = BeanUtils.getConstructor(beanClass, beanDefinition.getConstructorValues());
            bean =createBean(constructor,beanDefinition.getConstructorValues());
        }else{//无参构造，创建bean
            bean = beanClass.newInstance();
        }
        container.registerBean(id,bean);
        currentCreateBean.remove(className);
        return (T)bean;
    }


    /**
     *
     * 有参构造创建bean
     * @param constructor
     * @param constructorValues
     */
    public Object createBean(Constructor constructor,List<PropertyValue> constructorValues) throws IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {
            Parameter[] parameters = constructor.getParameters();
            Object[] initArgs = new Object[parameters.length];
            int i=0;
            for (Parameter parameter : parameters) {
                String name = parameter.getName();
                String type = parameter.getType().getSimpleName();
                for (PropertyValue constructorValue : constructorValues) {
                    if(constructorValue.getPropertyName().equals(name)){
                        if(constructorValue.isRef()){
                            Object refBean = container.getBean(constructorValue.getValue());
                            if(refBean != null){
                                initArgs[i]=refBean;
                            }else{
                                initArgs[i]=createBean(xmlConfiguration.getBeanDefinitions().get(constructorValue.getValue()));
                            }
                        }else {
                            initArgs[i]=BeanUtils.typeConvert(constructorValue.getValue(),type);
                        }

                        i++;
                    }
                }

            }
            return constructor.newInstance(initArgs);
    }


    /**
     *
     * 给bean的属性赋值
     * @param beanDefinition
     * @param bean
     */
    public void assembleBean(BeanDefinition beanDefinition,Object bean) throws NoSuchFieldException, IllegalAccessException {
        List<PropertyValue> propertyValues = beanDefinition.getPropertyValues();
        Class<?> beanClass = bean.getClass();
        for (PropertyValue propertyValue : propertyValues) {
            String fieldName = propertyValue.getPropertyName();
            String value = propertyValue.getValue();
            Field field=beanClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            if(propertyValue.isRef()){
                field.set(bean,container.getBean(value));
            }else{
                String type = field.getType().getSimpleName();
                field.set(bean,BeanUtils.typeConvert(value,type));
            }



        }



    }







}
