package com.myd.aop.config;

import com.myd.aop.AdviceType;
import com.myd.aop.filter.Pointcut;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author myd
 * @date 2021/8/6  15:08
 */

public class AspectConfig {

    private Object aspect;//在创建beanPostAfterInitProcessor时候初始化；

    private String id;

    private static AspectConfig aspectConfig;

    public static AspectConfig getAspectConfig(){
        if(aspectConfig == null){
            synchronized (AspectConfig.class){
                if(aspectConfig == null){
                    aspectConfig = new AspectConfig();
                }
            }
        }
        return aspectConfig;
    }

    private AspectConfig(){
    }

    private Map<AdviceType, Pointcut> adviceList;


    public Map<AdviceType, Pointcut> getAdviceList() {
        return adviceList;
    }

    public void addAdvice(String adviceType,String pointcut,String methodName){
        AdviceType type =  Enum.valueOf(AdviceType.class,adviceType);
        Pointcut advice = new Pointcut(pointcut,methodName);
        if(adviceList == null){
            adviceList  =new HashMap<>();
        }
        adviceList.put(type,advice);

    }

    /**
     *
     * 检查是否配置了切面
     * @return
     */
    public static boolean hasAspectConfig(){
        return aspectConfig!=null;
    }

    public AspectConfig setAspect(Object aspect) {
        this.aspect = aspect;
        return this;
    }

    public Object getAspect() {
        return aspect;
    }

    public String getId() {
        return id;
    }

    public AspectConfig setId(String id) {
        this.id = id;
        return this;
    }


}
