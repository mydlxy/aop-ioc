package com.myd.aop.proxy;

import com.myd.aop.advice.Advice;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author myd
 * @date 2021/8/6  2:46
 */

public class JDKProxy implements InvocationHandler {

    private Object target;//目标类
    private Advice advice;
    public JDKProxy(Object target,Advice advice){
        this.advice = advice;
        this.target = target;
    }

    public <T>T newInstance(){
        return (T)Proxy.newProxyInstance(target.getClass().getClassLoader(),target.getClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(advice.matchMethod(method))
            return advice.invoke(proxy,method,args,target);
        else
            return method.invoke(target,args);
    }


}
