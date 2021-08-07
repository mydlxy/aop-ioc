package com.myd.aop.advice;

import com.myd.aop.config.AspectConfig;
import com.myd.aop.filter.ClassFilter;
import com.myd.aop.filter.MethodFilter;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author myd
 * @date 2021/8/6  15:46
 */

public class BeforeAdvice implements Advice {
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
        beforeMethod = aspect.getClass().getMethod(methodName);
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
    public boolean supportsAdvice(Advice advice) {
        return advice instanceof BeforeAdvice;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args,Object target) throws Throwable {
        beforeMethod.invoke(aspect,null);
        return method.invoke(target,args);
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        beforeMethod.invoke(aspect,null);
        return methodProxy.invokeSuper(o,objects);

    }


}
