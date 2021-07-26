package com.myd.ioc.exception;

/**
 * @author myd
 * @date 2021/7/27  3:06
 */

public class PropertiesKeyNotFound extends RuntimeException {

    public PropertiesKeyNotFound(){}
    public PropertiesKeyNotFound(String message){
        super(message);
    }
    public PropertiesKeyNotFound(String message, Throwable cause){
        super(message,cause);
    }
    public PropertiesKeyNotFound(Throwable cause){
        super(cause);
    }

}
