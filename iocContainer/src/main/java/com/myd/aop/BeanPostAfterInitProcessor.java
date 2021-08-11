package com.myd.aop;
import com.myd.aop.advice.*;
import com.myd.aop.config.AspectConfig;
import com.myd.aop.filter.ClassFilter;
import com.myd.aop.proxy.CglibProxy;
import com.myd.aop.proxy.JDKProxy;
import com.myd.ioc.beans.IocContainer;

import java.util.*;

/**
 * @author myd
 * @date 2021/8/5  2:08
 */

public class BeanPostAfterInitProcessor extends IocContainer implements BeanPostProcessor {

    private AspectConfig aspectConfig;

    private BeforeAdvice beforeAdvice;

    private AfterAdvice afterAdvice;

    private AfterReturningAdvice afterReturningAdvice;

    private AfterThrowableAdvice afterThrowableAdvice;

    private AroundAdvice aroundAdvice;


    private static BeanPostAfterInitProcessor beanPostAfterInitProcessor;

    public static BeanPostAfterInitProcessor getBeanPostAfterInitProcessor() throws NoSuchMethodException {
        if(beanPostAfterInitProcessor == null){
            synchronized (BeanPostAfterInitProcessor.class){
                if(beanPostAfterInitProcessor == null)
                    beanPostAfterInitProcessor = new BeanPostAfterInitProcessor();
            }
        }
        return beanPostAfterInitProcessor;
    }
    private BeanPostAfterInitProcessor() throws NoSuchMethodException {
        initAdvice();
    }

    public void initAdvice() throws NoSuchMethodException {
        if(!AspectConfig.hasAspectConfig())return;
        aspectConfig = AspectConfig.getAspectConfig();
        String aspectId = aspectConfig.getId();
        Object aspect  =getBeans().get(aspectId);
        aspectConfig.setAspect(aspect);
        if(aspectConfig.getBefore()!=null)
            beforeAdvice = new BeforeAdvice();
        if(aspectConfig.getAfter()!= null)
            afterAdvice = new AfterAdvice();
        if(aspectConfig.getAfterReturning()!= null)
            afterReturningAdvice = new AfterReturningAdvice();
        if(aspectConfig.getAfterThrowable()!=null)
            afterThrowableAdvice = new AfterThrowableAdvice();
        if(aspectConfig.getAround()!= null)
            aroundAdvice = new AroundAdvice();
    }



    public Map<String, Object> iocContainer(){
        return getBeans();
    }

//    public void postProcessAllBean(){
//        Map<String, Object> beans = getBeans();
//        String aspectId = aspectConfig.getId();
//        for(Iterator<String> iter= beans.keySet().iterator();iter.hasNext();){
//            String key = iter.next();
//            if(key.equals(aspectId))continue;
//            Object bean = beans.get(key);
//            Object postBean = postProcessAfterInitialization(bean);
//            if(bean != postBean)
//                beans.put(key,postBean);
//        }
//    }






    /***
     *
     *
     * xml解析注入值时，检查pointcut的正确性
     *
     * @param bean
     * @return
     */

    @Override
    public Object postProcessAfterInitialization(Object bean) {
        List<Advice> advices = matchAdvice(bean);
        if(advices.isEmpty())return bean;
        return beanPostProcess(bean,advices);
    }
    public Object beanPostProcess(Object bean,List<Advice> advice){

        if(bean.getClass().getInterfaces().length==0){//bean没有实现接口，cglib代理
            CglibProxy cglibProxy = new CglibProxy(bean,advice);
            return cglibProxy.createInstance();
        }else{//实现了接口，用jdk动态代理
            JDKProxy jdkProxy = new JDKProxy(bean,advice);
            return jdkProxy.newInstance();
        }
    }


    public List<Advice> matchAdvice(Object bean){
        List<Advice> advices = new ArrayList<>();
        if(aroundAdvice != null && aroundAdvice.matchClass(bean)){
            advices.add(aroundAdvice);
        }
        if(beforeAdvice != null && beforeAdvice.matchClass(bean)){
            advices.add(beforeAdvice);
        }
        if(afterAdvice != null && afterAdvice.matchClass(bean)){
            advices.add(afterAdvice);
        }
        if(afterReturningAdvice != null && afterReturningAdvice.matchClass(bean)){
            advices.add(afterReturningAdvice);
        }
        if(afterThrowableAdvice != null && afterThrowableAdvice.matchClass(bean)){
            advices.add(afterThrowableAdvice);
        }

        return advices;
    }



}
