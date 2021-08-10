package com.myd.aop.advice;

import com.myd.aop.AdviceType;
import com.myd.aop.config.AspectConfig;
import com.myd.aop.filter.ClassFilter;
import com.myd.aop.filter.MethodFilter;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author myd
 * @date 2021/8/6  15:46
 */

public class BeforeAdvice implements Advice {
    Logger log = Logger.getLogger(getClass());
    private String pointcut;
    private Method beforeMethod;
    private Object aspect;
    public BeforeAdvice()throws NoSuchMethodException {
        init();
    }

    public void init() throws NoSuchMethodException {
        AspectConfig aspectConfig  =AspectConfig.getAspectConfig();
        this.aspect = aspectConfig.getAspect();
        Map<String,String> before = aspectConfig.getBefore();
        this.pointcut =  before.keySet().iterator().next();
        String methodName =  before.get(pointcut);
        try {
            beforeMethod = aspect.getClass().getMethod(methodName);
        }catch(NoSuchMethodException e){
            log.debug("aspect not found method:"+methodName);
            throw new NoSuchMethodException("aspect not found method:"+methodName);
        }
    }
    @Override
    public boolean matchMethod(Method method){
        return MethodFilter.matchMethod(method,pointcut);
    }

    @Override
    public boolean matchClass(Object bean) {
        return ClassFilter.matchClass(bean,pointcut);
    }


    @Override
    public AdviceType getType() {
        return AdviceType.Before;
    }

    @Override
    public void advice() throws InvocationTargetException, IllegalAccessException {
            beforeMethod.invoke(aspect,null);
    }



}
