package com.myd.aop.advice;

import com.myd.aop.AdviceType;
import com.myd.aop.filter.MethodFilter;
import com.myd.aop.filter.Pointcut;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author myd
 * @date 2021/8/18  16:45
 */

public class StandardAdvice implements Advice {

    private Object aspect;

    private Method advice;

    private AdviceType adviceType;

    private Pointcut pointcut;


    public StandardAdvice(AdviceType adviceType,Object aspect,Pointcut pointcut) throws NoSuchMethodException {
        this.aspect =aspect;
        this.pointcut = pointcut;
        this.adviceType = adviceType;
        this.advice = aspect.getClass().getDeclaredMethod(pointcut.getMethodName());
    }


    @Override
    public AdviceType getType() {
        return adviceType;
    }

    @Override
    public void advice() throws InvocationTargetException, IllegalAccessException {
        advice.invoke(aspect,null);
    }

    @Override
    public boolean matchMethod(Method method) {
        return pointcut.matchMethod(method);
    }

    @Override
    public boolean matchClass(Object bean) {
        return pointcut.matchClass(bean);
    }
}
