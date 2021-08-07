package com.myd.aop;

/**
 * @author myd
 * @date 2021/8/3  23:11
 */

public interface BeanPostProcessor {

    Object postProcessAfterInitialization(Object bean);
}
