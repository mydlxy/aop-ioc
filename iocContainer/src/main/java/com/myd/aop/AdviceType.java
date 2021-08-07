package com.myd.aop;

/**
 * @author myd
 * @date 2021/8/5  19:05
 */

public enum AdviceType {
    /*
    前置通知
     */
    Before,
    /*
    后置通知,在目标方法执行后，执行切面方法；即使目标方法发生异常也会执行
     */
    After,
    /*
    后置通知；目标方法发生异常不会执行
     */
    AfterReturning,

    /*
    异常时通知
     */
    AfterThrowable,
    /*
    环绕通知：方法执行前，后，抛出异常
     */
    Around;


    private String pointcut;
    public String getPointcut(){
        return pointcut;
    }
    public void setPointcut(String pointcut){
        this.pointcut = pointcut;
    }


}
