package com.myd.ioc.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author myd
 * @date 2021/7/27  0:49
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Configuration {
    /**
     * 在class上使用该注解，表示该类作为一个配置类；与@Bean一起使用
     */
}
