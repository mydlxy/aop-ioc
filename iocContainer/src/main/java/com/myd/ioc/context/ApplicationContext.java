package com.myd.ioc.context;

/**
 * @author myd
 * @date 2021/7/27  21:10
 */

public interface ApplicationContext {

    public <T>T getBean(String id);

    public <T>T getBean(Class classType);
}
