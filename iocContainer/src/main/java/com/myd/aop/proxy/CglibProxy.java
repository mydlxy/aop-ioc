package com.myd.aop.proxy;

import com.myd.aop.AdviceType;
import com.myd.aop.AopUtils;
import com.myd.aop.advice.Advice;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author myd
 * @date 2021/8/6  15:33
 */

public class CglibProxy implements MethodInterceptor {

    private Object target;

    private List<Advice> advices;

    public CglibProxy(Object target,List<Advice> advices){
        this.target = target;
        this.advices = advices;
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

        Object returnVal;
        boolean exeFinally=true;
        try {
            if(!exeAdvice(method, AdviceType.Around)){
                exeAdvice(method,AdviceType.Before);
            }

            returnVal = methodProxy.invokeSuper(o, objects);

            if(exeAdvice(method,AdviceType.Around)){
                exeFinally=false;
            }else{
                if(exeAdvice(method,AdviceType.AfterReturning))
                    exeFinally=false;
            }
        }catch(Throwable e){
            if(exeAdvice(method,AdviceType.Around)){
                exeFinally=false;
            }else{
                if(exeAdvice(method,AdviceType.AfterThrowable))
                    exeFinally=false;
            }
            throw new Throwable(e);
        }finally {
            if(exeFinally)
                exeAdvice(method,AdviceType.After);
        }
        return returnVal;

    }

    public boolean exeAdvice(Method method,AdviceType type) throws InvocationTargetException, IllegalAccessException {

        boolean exeAdvice=false;
        if(AopUtils.containAdvice(type,advices) ){
            Advice advice = AopUtils.getAdvice(type,advices);
            exeAdvice = advice.matchMethod(method);
            if(exeAdvice)
                advice.advice();
        }
        return exeAdvice;

    }
}
