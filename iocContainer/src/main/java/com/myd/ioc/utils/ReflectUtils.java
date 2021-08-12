package com.myd.ioc.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author myd
 * @date 2021/7/31  0:57
 */

public class ReflectUtils {



    public static void setValue(Object bean ,Field field,Object value) throws IllegalAccessException {
        field.setAccessible(true);
        field.set(bean,value);
    }

    /**
     * 返回class的Modifier
     *
     * @param beanClass
     * @return
     */
    public static String getModifier(Class beanClass){
        int modifiers = beanClass.getModifiers();
        return Modifier.toString(modifiers);
    }

    /**
     * beanClass.toGenericString() =======>  modifier +  name;
     * @param beanClass
     * @return
     */
    public static boolean isClass(Class beanClass){
        String modifier = getModifier(beanClass);
        if(modifier.matches(".*(abstract|interface).*"))return false;
        if(beanClass.getName().matches(".*enum.*"))return false;
        return true;
    }


}
