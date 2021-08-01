package com.myd.ioc.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author myd
 * @date 2021/7/27  0:54
 */

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Param {
    /**
     * 作用在参数上，注入类型Object其他bean的id
     * @return
     */
    String refId() default "";

    /**
     *
     * 值类型：8 个基础类型， 8个包装类，String可以直接赋值
     *
     * @return
     */

    String value() default "";

}
