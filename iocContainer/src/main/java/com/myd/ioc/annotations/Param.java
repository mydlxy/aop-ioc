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
     * 作用在参数上，注入其他bean的id
     * @return
     */
    String refId();
}
