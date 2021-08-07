package com.myd.ioc.utils;

import java.lang.reflect.Field;

/**
 * @author myd
 * @date 2021/7/31  0:57
 */

public class ReflectUtils {



    public static void setValue(Object bean ,Field field,Object value) throws IllegalAccessException {
        field.setAccessible(true);
        field.set(bean,value);

    }


}
