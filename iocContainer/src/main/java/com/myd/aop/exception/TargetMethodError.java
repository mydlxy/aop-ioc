package com.myd.aop.exception;

/**
 * @author myd
 * @date 2021/8/18  18:01
 */

public class TargetMethodError extends RuntimeException {

    public TargetMethodError(){}
    public TargetMethodError(String message){
        super(message);
    }
    public TargetMethodError(String message, Throwable cause){
        super(message,cause);
    }
    public TargetMethodError(Throwable cause){
        super(cause);
    }


}
