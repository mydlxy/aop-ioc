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

//    private static final String EXECUTION ="\\s*execution\\s*\\(\\s*(public|protected|private|\\*)\\s+(((\\*?\\w+\\*?|\\*)\\.)+\\.?)?(\\*?\\w+\\*?|\\*)\\(\\s*(\\.\\.|([A-Z]\\w*|int|double|float|char|byte|long|short|boolean)(\\[\\])*\\s*(,\\s*([A-Z]\\w*|int|double|float|char|byte|long|short|boolean)(\\[\\])*\\s*)*|)\\)\\s*\\)\\s*";
    private static final String EXECUTION;

    private static final String modifier;

    private static final String packagePath;

    private static final String methodName;

    private static final String methodParamList;

    private static final String methodSingleParamType;

    private static final String methodAllParamType;

    private static final String methodNoParam;
    static {
        modifier = "(public|protected|private|\\*)";
        packagePath = "(((\\*?\\w+\\*?|\\*)\\.)+\\.?)?";
        methodName = "(\\*?\\w+\\*?|\\*)";

        methodSingleParamType = "\\s*([A-Z]\\w*|int|double|float|char|byte|long|short|boolean)(\\[\\])*\\s*";
        methodAllParamType = "..";
        methodNoParam = "";
        methodParamList = "("+methodAllParamType+"|"+methodSingleParamType+"(,"+methodSingleParamType+")*|"+methodNoParam+")";

        EXECUTION = "\\s*execution\\s*\\(\\s*"+modifier+"\\s+"+packagePath+methodName+"\\("+methodParamList+"\\)\\s*\\)\\s*";

    }

    public static String getExecution(){
       return EXECUTION;
    }

    private Object aspect;

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
