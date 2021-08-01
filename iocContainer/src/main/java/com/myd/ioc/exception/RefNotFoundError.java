package com.myd.ioc.exception;

/**
 * @author myd
 * @date 2021/7/30  13:42
 */

/**
 *
 * xml中ref的引用值错误
 *
 * e.g.
 * xml中配置bean
 * <bean id="a" class="A"/>
 * <bean id="b" class="B">
 *     <property name="b" ref="d"/>
 *</bean>
 *
 *id = b的bean中引用 d; --->xml中没有配置id=d的bean
 *
 */
public class RefNotFoundError extends RuntimeException {

    public RefNotFoundError(String message){
        super(message);
    }

    public RefNotFoundError(){}

    public RefNotFoundError(String message, Throwable cause){
        super(message,cause);
    }

    public RefNotFoundError(Throwable cause){
        super(cause);
    }


}
