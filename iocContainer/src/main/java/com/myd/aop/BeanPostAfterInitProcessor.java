package com.myd.aop;
import com.myd.aop.advice.*;
import com.myd.aop.config.AspectConfig;
import com.myd.aop.filter.ClassFilter;
import com.myd.aop.filter.Pointcut;
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

    private List<Advice> adviceList = new ArrayList<>();

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
        Map<AdviceType, Pointcut> adviceMap = aspectConfig.getAdviceList();
        if(adviceMap == null || adviceMap.isEmpty())return;
        for(Iterator<AdviceType> iter = adviceMap.keySet().iterator();iter.hasNext();){
           AdviceType type = iter.next();
           Pointcut pointcut = adviceMap.get(type);
           Advice advice = new StandardAdvice(type,aspect,pointcut);
           adviceList.add(advice);
        }

    }



    public Map<String, Object> iocContainer(){
        return getBeans();
    }






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
        for (Advice advice : adviceList) {
           if(advice.matchClass(bean))advices.add(advice);
        }
        return advices;
    }



}
