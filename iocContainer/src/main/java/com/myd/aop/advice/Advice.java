package com.myd.aop.advice;

import com.myd.aop.AdviceType;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author myd
 * @date 2021/8/6  15:49
 */

public interface Advice {


    AdviceType getType();

    void advice() throws InvocationTargetException, IllegalAccessException;

    boolean matchMethod(Method method);

    boolean matchClass(Object bean);

    }
