package com.myd.aop;

import com.myd.aop.advice.Advice;
import com.myd.ioc.annotations.Value;
import com.myd.ioc.beans.IocContainer;
import com.myd.ioc.utils.ReflectUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author myd
 * @date 2021/8/9  22:28
 */

public class AopUtils {



    /**
     *
     * 获取@Value注解,被cglib代理的类也可以获取；被JDK动态代理的就不行了
     * @param bean
     * @return
     */
    public static List<Field> getBeanValue(Class bean){
        List<Field> values =new ArrayList<>();
        Field[] fields = bean.getDeclaredFields();
        for (final Field field : fields) {
            field.setAccessible(true);
            Value value = field.getAnnotation(Value.class);
            if(value == null)continue;
            values.add(field);
        }
        if(bean.getSuperclass() != null  &&
           !bean.getSuperclass().equals(Object.class)){
           return getBeanValue(bean.getSuperclass());
        }
         return values;
    }



    public static List<Field> findFieldAnnotation(Class bean,Class AnnotationType){
        List<Field> values =new ArrayList<>();
        Field[] fields = bean.getDeclaredFields();
        for (final Field field : fields) {
            field.setAccessible(true);
            Annotation value = field.getAnnotation(AnnotationType);
            if(value == null)continue;
//            System.out.println(field.getClass());
            values.add(field);
        }
        if(bean.getSuperclass() != null  &&
                !bean.getSuperclass().equals(Object.class)){
            return findFieldAnnotation(bean.getSuperclass(),AnnotationType);
        }
        return values;
    }



    /**
     *
     * 返回JDK动态代理对象的目标 对象：target
     * @param bean：JDK动态代理生成的动态代理对象
     * @return
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     */
    public static Object findJdkTarget(Object bean) throws ClassNotFoundException, IllegalAccessException, NoSuchFieldException {

        Field h = bean.getClass().getSuperclass().getDeclaredField("h");
        h.setAccessible(true);
        Object jdkProxy = h.get(bean);
        Field targetField = jdkProxy.getClass().getDeclaredField("target");
        targetField.setAccessible(true);
        //原生对象
        Object target = targetField.get(jdkProxy);
        return target;
    }


    /**
     *
     * cglib代理 或 没有被代理的类
     * @param bean
     * @param fields
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     */
    public static void injectAutowired(Object bean,List<Field> fields) throws ClassNotFoundException, IllegalAccessException, NoSuchFieldException {
        IocContainer container = IocContainer.container();
        //com.sun.proxy.$Proxy9--->一般jdk动态代理类的类名是：com.sun.proxy.$Proxy+数字
        if(bean.getClass().getName().matches("com\\.sun\\.proxy\\.\\$Proxy.*")){
            bean =findJdkTarget(bean);
        }
        for (Field field : fields) {
            String typeName = field.getType().getTypeName();
            Object value = container.getBean(Class.forName(typeName));
            ReflectUtils.setValue(bean,field,value);
        }
    }


    public static boolean containAdvice(AdviceType type, List<Advice> advices){
        if(advices == null)return false;
        for ( Advice advice : advices) {
            if(advice.getType().equals(type))return true;
        }
        return false;
    }

    public static Advice getAdvice(AdviceType type, List<Advice> advices){
        if(advices == null)return null;
        for (final Advice advice : advices) {
            if(advice.getType().equals(type))return advice;
        }
        return null;
    }




}
