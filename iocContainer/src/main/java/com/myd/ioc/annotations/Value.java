package com.myd.ioc.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author myd
 * @date 2021/7/28  11:15
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Value {

    /**
     *
     *
     * 基础类型,包装类型,String的字段可以用@Value来注解；
     * 可以使用 '${}' 引用properties定义的值；
     *
     *
     * @return
     */
    String value();
}
