package com.myd.aop.advice;

import com.myd.aop.config.AspectConfig;
import com.myd.aop.filter.ClassFilter;
import com.myd.aop.filter.MethodFilter;
import net.sf.cglib.proxy.MethodProxy;

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
    public boolean supportsAdvice(Advice advice) {
        return advice instanceof AfterThrowableAdvice;
    }

    @Override
    public boolean matchMethod(Method method) {
        return MethodFilter.matchMethod(method,pointcut);
    }

    @Override
    public boolean matchClass(Object bean) {
        return  ClassFilter.matchClass(bean,pointcut);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args, Object target) throws Throwable {

       try{
           return method.invoke(target,args);
       }catch (Exception e){
           afterThrowableMethod.invoke(aspect,null);
           throw new Throwable(e);
       }
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        try{
            return methodProxy.invokeSuper(o,objects);
        }catch (Exception e){
            afterThrowableMethod.invoke(aspect,null);
            throw new Throwable(e);
        }

    }
}
