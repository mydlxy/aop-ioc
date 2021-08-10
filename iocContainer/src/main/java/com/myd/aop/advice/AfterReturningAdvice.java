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
 * @date 2021/8/6  18:38
 */

public class AfterReturningAdvice implements Advice {

    private String pointcut;
    private Method afterReturningMethod;
    private Object aspect;

    public  AfterReturningAdvice(String pointcut,Object aspect,Method afterReturningMethod){
        this.pointcut = pointcut;
        this.aspect = aspect;
        this.afterReturningMethod = afterReturningMethod;
    }

    public AfterReturningAdvice() throws NoSuchMethodException {
        init();
    }
    public void init() throws NoSuchMethodException {
        AspectConfig aspectConfig  =AspectConfig.getAspectConfig();
        this.aspect = aspectConfig.getAspect();
        Map<String,String> afterReturning = aspectConfig.getAfterReturning();
        this.pointcut =  afterReturning.keySet().iterator().next();
        String methodName =  afterReturning.get(pointcut);
        afterReturningMethod = aspect.getClass().getMethod(methodName);
    }


    @Override
    public AdviceType getType() {
        return AdviceType.AfterReturning;
    }

    @Override
    public void advice() throws InvocationTargetException, IllegalAccessException {
        afterReturningMethod.invoke(aspect,null);
    }


    @Override
    public boolean matchMethod(Method method) {
        return MethodFilter.matchMethod(method,pointcut);
    }

    @Override
    public boolean matchClass(Object bean) {
        return ClassFilter.matchClass(bean,pointcut);
    }

}
