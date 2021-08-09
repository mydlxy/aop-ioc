package com.myd.aop;

import com.myd.aop.proxy.CglibProxy;
import com.myd.aop.proxy.JDKProxy;

/**
 * @author myd
 * @date 2021/8/9  22:30
 */

/**
 *
 * 代理类型
 */
public enum  ProxyType {
    /**
     *
     * cglib代理类型
     */
    CglibProxy,
    /**
     *
     * jdk动态代理类型
     *
     */
    JdkProxy
}
