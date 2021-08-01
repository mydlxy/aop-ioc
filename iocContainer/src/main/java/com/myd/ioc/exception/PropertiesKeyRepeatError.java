package com.myd.ioc.exception;

/**
 * @author myd
 * @date 2021/8/1  15:56
 */

public class PropertiesKeyRepeatError extends RuntimeException {
    public PropertiesKeyRepeatError(){}
    public PropertiesKeyRepeatError(String message){
        super(message);
    }
    public PropertiesKeyRepeatError(String message, Throwable cause){
        super(message,cause);
    }
    public PropertiesKeyRepeatError(Throwable cause){
        super(cause);
    }


}
