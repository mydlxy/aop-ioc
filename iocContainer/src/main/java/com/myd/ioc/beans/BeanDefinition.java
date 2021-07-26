package com.myd.ioc.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * @author myd
 * @date 2021/7/24  0:34
 */

public class BeanDefinition {

    private String id;

    private String className;

    private List<PropertyValue> propertyValues;


    public BeanDefinition(String id, String className, List<PropertyValue> propertyValues) {
        this.id = id;
        this.className = className;
        this.propertyValues = propertyValues;
    }

    public BeanDefinition(){}

    @Override
    public String toString() {
        return "BeanDefinition{" +
                "id='" + id + '\'' +
                ", className='" + className + '\'' +
                ", propertyValues=" + propertyValues +
                '}';
    }

    public String getId() {
        return id;
    }

    public BeanDefinition setId(String id) {
        this.id = id;
        return this;
    }

    public String getClassName() {
        return className;
    }

    public BeanDefinition setClassName(String className) {
        this.className = className;
        return this;
    }

    public List<PropertyValue> getPropertyValues() {
        return propertyValues;
    }

    public BeanDefinition setPropertyValues(List<PropertyValue> propertyValues) {
        this.propertyValues = propertyValues;
        return this;
    }
}
