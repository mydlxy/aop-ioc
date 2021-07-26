package com.myd.ioc.beans;

/**
 * @author myd
 * @date 2021/7/24  0:33
 */

public class PropertyValue {

    private String propertyName;

    private String value;

    private boolean ref;//值是否是ref类型;

    private boolean constructor;//判断标签:constructor

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isRef() {
        return ref;
    }

    public void setRef(boolean ref) {
        this.ref = ref;
    }

    public boolean isConstructor() {
        return constructor;
    }

    public void setConstructor(boolean constructor) {
        this.constructor = constructor;
    }
}
