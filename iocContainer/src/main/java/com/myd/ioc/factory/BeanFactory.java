package com.myd.ioc.factory;

import com.myd.ioc.beans.BeanDefinition;

import java.lang.reflect.InvocationTargetException;

/**
 * @author myd
 * @date 2021/7/28  1:32
 */

public interface BeanFactory {

    public <T> T createBean(BeanDefinition beanDefinition) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException;
}

