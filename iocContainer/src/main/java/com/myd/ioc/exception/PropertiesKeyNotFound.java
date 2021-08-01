package com.myd.ioc.exception;

/**
 * @author myd
 * @date 2021/7/27  3:06
 */

/**
 *
 * <bean>的属性引用properties文件的key-value，找不到对应的key值报错；
 *
 *
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
