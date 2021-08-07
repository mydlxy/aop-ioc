package com.myd.aop.advice;

import com.myd.aop.config.AspectConfig;
import com.myd.aop.filter.ClassFilter;
import com.myd.aop.filter.MethodFilter;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author myd
 * @date 2021/8/6  18:48
 */

public class AroundAdvice implements Advice {

    private String pointcut;
    private Method aroundMethod;
    private Object aspect;

    public AroundAdvice() throws NoSuchMethodException {
        init();
    }
    public void init() throws NoSuchMethodException {
        AspectConfig aspectConfig  =AspectConfig.getAspectConfig();
        this.aspect = aspectConfig.getAspect();
        Map<String,String> around = aspectConfig.getAround();
        this.pointcut =  around.keySet().iterator().next();
        String methodName =  around.get(pointcut);
        aroundMethod = aspect.getClass().getMethod(methodName);
    }



    @Override
    public boolean supportsAdvice(Advice advice) {
        return advice instanceof AroundAdvice;
    }

    @Override
    public boolean matchMethod(Method method) {
        return MethodFilter.matchMethod(method,pointcut);
    }

    @Override
    public boolean matchClass(Object bean) {
        return ClassFilter.matchClass(bean,pointcut);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args, Object target) throws Throwable {

        Object returnVal;
        try{
            aroundMethod.invoke(aspect,null);
            returnVal = method.invoke(target,args);
            aroundMethod.invoke(aspect,null);
        }catch (Exception e){
            aroundMethod.invoke(aspect,null);
            throw new Exception(e);
        }
        return returnVal;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Object returnVal;
        try{
            aroundMethod.invoke(aspect,null);
            returnVal = methodProxy.invokeSuper(o,objects);
            aroundMethod.invoke(aspect,null);
        }catch (Exception e){
            aroundMethod.invoke(aspect,null);
            throw new Exception(e);
        }
        return returnVal;

    }
}
