package com.myd.aop.proxy;

import com.myd.aop.advice.Advice;
import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author myd
 * @date 2021/8/6  15:33
 */

public class CglibProxy implements MethodInterceptor {

    private Object target;

    private Advice advice;

    public CglibProxy(Object target,Advice advice){
        this.target = target;
        this.advice = advice;
    }


    /**
     *
     * 生成代理类
     * @param <T>
     * @return
     */
    public <T>T createInstance(){
        Enhancer enhancer  = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);
        return (T) enhancer.create();
    }



    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
      //临时添加，用于测试；记得删除
        if(advice == null)
            return methodProxy.invokeSuper(o,objects);


        if(advice.matchMethod(method))
            return advice.intercept(o,method,objects,methodProxy);
        else
            return methodProxy.invokeSuper(o,objects);
    }
}
