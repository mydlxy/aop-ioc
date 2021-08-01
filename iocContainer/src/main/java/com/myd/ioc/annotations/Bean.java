package com.myd.ioc.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author myd
 * @date 2021/7/27  0:48
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Bean {


    /**
     * 方法的返回值注入到ioc容器中
     * bean默认id是方法名
     * e.g.
     * @Bean
     * public A getA(){
     *     return new A();
     * }
     * 将A对象，注入到容器:container.put(getA,new A());
     * 如果使用@Bean("a")==> container.put(a,new A());
     * @return
     */
    String id() default "";
}
