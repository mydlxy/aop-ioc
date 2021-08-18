package com.myd.aop.exception;

/**
 * @author myd
 * @date 2021/8/12  17:13
 */

public class PointcutError extends RuntimeException{
    public PointcutError(){}
    public PointcutError(String message){
        super(message);
    }
    public PointcutError(String message, Throwable cause){
        super(message,cause);
    }
    public PointcutError(Throwable cause){
        super(cause);
    }

}
