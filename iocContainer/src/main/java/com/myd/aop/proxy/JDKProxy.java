package com.myd.aop.proxy;

import com.myd.aop.AdviceType;
import com.myd.aop.AopUtils;
import com.myd.aop.advice.Advice;
import com.myd.aop.exception.TargetMethodError;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @author myd
 * @date 2021/8/6  2:46
 */

public class JDKProxy implements InvocationHandler {

    private Object target;//目标类
    private List<Advice> advices;
    public JDKProxy(Object target,List<Advice> advices){
        this.advices = advices;
        this.target = target;
    }

    public <T>T newInstance(){
        return (T)Proxy.newProxyInstance(target.getClass().getClassLoader(),target.getClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //测试时，临时添加；
        Object returnVal=null;
        boolean exeFinally=true;
        try {
            if(!exeAdvice(method, AdviceType.Around)){
                exeAdvice(method,AdviceType.Before);
            }

            try {
                returnVal = method.invoke(target, args);
            }catch (Throwable e){
                throw new TargetMethodError(e);
            }

            if(exeAdvice(method,AdviceType.Around )){
                exeFinally=false;
            }else{
                if(exeAdvice(method,AdviceType.AfterReturning))
                    exeFinally=false;
            }
        }catch(Throwable e){
            if(e instanceof TargetMethodError)
                if(exeAdvice(method,AdviceType.Around)){
                    exeFinally=false;
                }else{
                    if(exeAdvice(method,AdviceType.AfterThrowable))
                        exeFinally=false;
                }
            else
                exeFinally=false;
            throw new Throwable(e);
        }finally {
            if(exeFinally)
                exeAdvice(method,AdviceType.After);
        }
        return returnVal;

    }




    public boolean exeAdvice(Method method, AdviceType type) throws InvocationTargetException, IllegalAccessException {
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
