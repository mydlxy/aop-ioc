package com.myd.ioc.exception;

/**
 * @author myd
 * @date 2021/7/28  23:31
 */

public class MatchConstructorError extends RuntimeException {

    public MatchConstructorError(String message){
        super(message);
    }

    public MatchConstructorError(){}

    public MatchConstructorError(String message, Throwable cause){
        super(message,cause);
    }

    public MatchConstructorError(Throwable cause){
        super(cause);
    }

}
