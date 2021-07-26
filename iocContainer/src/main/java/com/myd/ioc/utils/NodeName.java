package com.myd.ioc.utils;

/**
 * @author myd
 * @date 2021/7/27  0:32
 */

/**
 *
 * 支持的标签
 *property-placeholder
 */
public enum NodeName {
    /**
     *
     * beans：根标签
     */
    beans,
    /**
     *
     * bean：加载类
     */
    bean,
    /**
     *bean的子标签，
     * 加载属性值
     *
     */
    property,
    /**
     * bean子标签
     * 创建bean时构造方法参数值
     *
     */
    constructor,
    /**
     * 加载properties文件
     *
     */
    propertyPlaceholder,
    /**
     *
     * 注解，
     * <ComponentScan package=""/>
     *
     *  package:指定扫描包
     */
    ComponentScan
}
