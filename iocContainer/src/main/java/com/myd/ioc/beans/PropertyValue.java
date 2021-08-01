package com.myd.ioc.beans;

/**
 * @author myd
 * @date 2021/7/24  0:33
 */

/**
 *
 * bean 标签中每一个标签对应一个PropertyValue 对象<br>
 *
 * e.g.
 *
 * <bean>
 *     <constructor name="" value=""/>
 *     <constructor name="" ref=""/>
 *     <property name="" value=""/>
 *     <property name="" ref=""/>
 * </bean>
 *
 * 在解析xml的bean标签时，会生成4个PropertyValue对象
 *
 */
public class PropertyValue {

    private String propertyName;

    private String value;

    private boolean ref;//值是否是ref类型;

    public String getPropertyName() {
        return propertyName;
    }


    public PropertyValue setPropertyName(String propertyName) {
        this.propertyName = propertyName;
        return this;
    }

    public String getValue() {
        return value;
    }

    public PropertyValue setValue(String value) {
        this.value = value;
        return this;
    }

    public boolean isRef() {
        return ref;
    }

    public PropertyValue setRef(boolean ref) {
        this.ref = ref;
        return this;
    }
}
