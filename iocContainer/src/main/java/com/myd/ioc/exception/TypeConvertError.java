package com.myd.ioc.exception;

/**
 * @author myd
 * @date 2021/7/28  0:44
 */

/**
 *
 * 值类型转换错误；
 */
public class TypeConvertError extends RuntimeException{
    public TypeConvertError(){}
    public TypeConvertError(String message){
        super(message);
    }
    public TypeConvertError(String message, Throwable cause){
        super(message,cause);
    }
    public TypeConvertError(Throwable cause){
        super(cause);
    }


}
