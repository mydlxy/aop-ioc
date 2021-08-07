package com.myd.aop.config;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author myd
 * @date 2021/8/6  15:08
 */

public class AspectConfig {

    private static final String EXECUTION ="\\s*execution\\s*\\(\\s*(public|protected|private|\\*)\\s+(((\\*?\\w+\\*?|\\*)\\.)+\\.?)?(\\*?\\w+\\*?|\\*)\\(\\s*(\\.\\.|([A-Z]\\w*|int|double|float|char|byte|long|short|boolean)(\\[\\])*\\s*(,\\s*([A-Z]\\w*|int|double|float|char|byte|long|short|boolean)(\\[\\])*\\s*)*|)\\)\\s*\\)\\s*";

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

    /**
     * Map<pointcut,methodName>
     *     目标方法执行之前执行
     *      aspect.m();
     *      target.method();
     */
    private Map<String,String> before ;

    /**
     * Map<pointcut,methodName>
     *     目标方法发生异常也会执行
     *     try{
     *         target.method();
     *     }finally{
     *         aspect.method();
     *     }
     *
     *
     */
    private Map<String,String> after ;

    /**
     * Map<pointcut,methodName>
     *     目标方法执行完之后执行
     *
     *     target.method();
     *     aspect.method;
     */
    private Map<String,String> afterReturning  ;

    /**
     * Map<pointcut,methodName>
     *     发生异常执行
     *     try{
     *         target.method();
     *     }catch(Exception e){
     *         aspect.method();
     *     }
     */
    private Map<String,String> afterThrowable  ;



    /**
     * Map<pointcut,methodName>
     *    前，后，异常，
     *
     *    try{
     *        aspect.method();
     *        target.method();
     *        aspect.method();
     *    }catch(Exception e){
     *        aspect.method()
     *    }
     */
    private Map<String,String> around ;


    /**
     *
     * 剪切掉表达式种多余空格；
     *
     * @param pointcut
     * @return
     */
    public String trim(String pointcut){
        String pointTrim = pointcut.replaceAll(" ","");
        Pattern pattern = Pattern.compile("(public|private|protected|\\*)");
        Matcher matcher = pattern.matcher(pointTrim);
        String regex=null ;
        if(matcher.find()){
            regex = matcher.group();
        }
        if(regex.equals("*")){
            regex="\\*";
        }
        String  replace = regex +"  ";
        return pointTrim.replaceFirst(regex,replace);

    }

    /**
     *
     *
     * 检查切面达式是否正确
     * @param pointcut
     */
    public  void checkPointcut(String pointcut){
        if(pointcut==null)
            throw new NullPointerException("pointcut is null error");
        if(!pointcut.matches(EXECUTION))
            throw new RuntimeException("pointcut expression error;pointcut="+pointcut);
    }

    /**
     *
     * 将pointcut中多余的空格去掉;
     *
     * @param pointcut
     * @return
     */
    public String formatPointcut(String pointcut){
        checkPointcut(pointcut);
        return trim(pointcut);
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


    public Map<String, String> getBefore() {
        return before;
    }

    public AspectConfig setBefore(Map<String, String> before) {
        this.before = before;
        return this;
    }

    public Map<String, String> getAfter() {
        return after;
    }

    public AspectConfig setAfter(Map<String, String> after ) {
        this.after = after;
        return this;
    }


    public Map<String, String> getAfterReturning() {
        return afterReturning;
    }

    public AspectConfig setAfterReturning(Map<String, String> afterReturning) {
        this.afterReturning = afterReturning;
        return this;
    }

    public Map<String, String> getAfterThrowable() {
        return afterThrowable;
    }

    public AspectConfig setAfterThrowable(Map<String, String> afterThrowable) {
        this.afterThrowable = afterThrowable;
        return this;
    }

    public Map<String, String> getAround() {
        return around;
    }

    public AspectConfig setAround(Map<String, String> around) {
        this.around = around;
        return this;
    }
}
