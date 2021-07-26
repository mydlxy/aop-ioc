package com.myd.ioc.exception;

/**
 * @author myd
 * @date 2021/7/27  0:28
 */

/**
 * 标签名错误
 * 支持标签类型在枚举类：NodeName 中定义
 *
 */
public class XmlLabelNameError extends RuntimeException {


    public XmlLabelNameError(){}
    public XmlLabelNameError(String message){
        super(message);
    }
    public XmlLabelNameError(String message, Throwable cause){
        super(message,cause);
    }
    public XmlLabelNameError(Throwable cause){
        super(cause);
    }



}
