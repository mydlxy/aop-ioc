package com.myd.aop.advice;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author myd
 * @date 2021/8/6  15:49
 */

public interface Advice {

    boolean supportsAdvice(Advice advice);

    public boolean matchMethod(Method method);

    public boolean matchClass(Object bean);

    Object invoke(Object proxy, Method method, Object[] args,Object target) throws Throwable;

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable ;


    }
