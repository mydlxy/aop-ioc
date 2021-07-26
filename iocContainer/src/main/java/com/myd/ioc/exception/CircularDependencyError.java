package com.myd.ioc.exception;

/**
 * @author myd
 * @date 2021/7/27  1:05
 */

/**
 *
 * 加载bean时,循环依赖错误
 *
 */

public class CircularDependencyError extends RuntimeException {
    public CircularDependencyError(){}
    public CircularDependencyError(String message){
        super(message);
    }
    public CircularDependencyError(String message, Throwable cause){
        super(message,cause);
    }
    public CircularDependencyError(Throwable cause){
        super(cause);
    }


}
