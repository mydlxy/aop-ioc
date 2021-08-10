package com.myd.aop.advice;

import com.myd.aop.AdviceType;
import com.myd.aop.config.AspectConfig;
import com.myd.aop.filter.ClassFilter;
import com.myd.aop.filter.MethodFilter;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Spliterator;

/**
 * @author myd
 * @date 2021/8/6  18:33
 */

public class AfterAdvice implements Advice {
    Logger log = Logger.getLogger(getClass());
    private String pointcut;
    private Method afterMethod;
    private Object aspect;

    public  AfterAdvice(String pointcut,Object aspect,Method afterMethod){
        this.pointcut = pointcut;
        this.aspect = aspect;
        this.afterMethod = afterMethod;
    }



    public AfterAdvice()throws NoSuchMethodException {
        init();
    }

    public void init() throws NoSuchMethodException {
        AspectConfig aspectConfig  =AspectConfig.getAspectConfig();
        this.aspect = aspectConfig.getAspect();
        Map<String,String> after = aspectConfig.getAfter();
        this.pointcut =  after.keySet().iterator().next();
        String methodName =  after.get(pointcut);
        try {
            afterMethod = aspect.getClass().getMethod(methodName);
        }catch (NoSuchMethodException e){
            log.debug("aspect not found method:"+methodName);
            throw new NoSuchMethodException("aspect not found method:"+methodName);
        }
    }

    @Override
    public AdviceType getType() {
        return AdviceType.After;
    }

    @Override
    public void advice() throws InvocationTargetException, IllegalAccessException {
        afterMethod.invoke(aspect,null);

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
