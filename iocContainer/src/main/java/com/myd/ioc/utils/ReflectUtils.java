package com.myd.ioc.utils;

import com.myd.ioc.annotations.Configuration;

import java.lang.reflect.Field;
import java.util.List;

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
