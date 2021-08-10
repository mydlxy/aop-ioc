package com.myd.aop.advice;

import com.myd.aop.AdviceType;
import com.myd.aop.config.AspectConfig;
import com.myd.aop.filter.ClassFilter;
import com.myd.aop.filter.MethodFilter;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author myd
 * @date 2021/8/6  18:42
 */

public class AfterThrowableAdvice implements Advice{

    private String pointcut;
    private Method afterThrowableMethod;
    private Object aspect;

    public  AfterThrowableAdvice(String pointcut,Object aspect,Method afterThrowableMethod){
        this.pointcut = pointcut;
        this.aspect = aspect;
        this.afterThrowableMethod = afterThrowableMethod;
    }

    public AfterThrowableAdvice() throws NoSuchMethodException {
        init();
    }
    public void init() throws NoSuchMethodException {
        AspectConfig aspectConfig  =AspectConfig.getAspectConfig();
        this.aspect = aspectConfig.getAspect();
        Map<String,String> afterThrowable = aspectConfig.getAfterThrowable();
        this.pointcut =  afterThrowable.keySet().iterator().next();
        String methodName =  afterThrowable.get(pointcut);
        afterThrowableMethod = aspect.getClass().getMethod(methodName);
    }


    @Override
    public AdviceType getType() {
        return AdviceType.AfterThrowable;
    }

    @Override
    public void advice() throws InvocationTargetException, IllegalAccessException {
        afterThrowableMethod.invoke(aspect,null);
    }

    @Override
    public boolean matchMethod(Method method) {
        return MethodFilter.matchMethod(method,pointcut);
    }

    @Override
    public boolean matchClass(Object bean) {
        return  ClassFilter.matchClass(bean,pointcut);
    }

}
