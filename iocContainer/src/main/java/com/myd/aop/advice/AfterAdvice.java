package com.myd.aop.advice;

import com.myd.aop.config.AspectConfig;
import com.myd.aop.filter.ClassFilter;
import com.myd.aop.filter.MethodFilter;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author myd
 * @date 2021/8/6  18:33
 */

public class AfterAdvice implements Advice {
    Logger log = Logger.getLogger(getClass());
    private String pointcut;
    private Method afterMethod;
    private Object aspect;

    @Override
    public boolean supportsAdvice(Advice advice) {
        return advice instanceof AfterAdvice;
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
    public boolean matchMethod(Method method) {
        return MethodFilter.matchMethod(method,pointcut);
    }

    @Override
    public boolean matchClass(Object bean) {
        return ClassFilter.matchClass(bean,pointcut);
    }

    /**
     * JDKProxy
     *
     * @param proxy
     * @param method
     * @param args
     * @param target
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args, Object target) throws Throwable {

        try{
            return method.invoke(target,args);
        }finally {
            afterMethod.invoke(aspect,null);
        }

    }

    /**
     *
     *
     * cglibProxy
     * @param o
     * @param method
     * @param objects
     * @param methodProxy
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        try{
            return  methodProxy.invokeSuper(o,objects);
        }finally {
            afterMethod.invoke(aspect,null);
        }
    }
}
