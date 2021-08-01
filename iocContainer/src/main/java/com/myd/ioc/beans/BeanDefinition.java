package com.myd.ioc.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * @author myd
 * @date 2021/7/24  0:34
 */

/**
 * 在xml中一个bean标签对应一个：BeanDefinition对象
 *
 *
 */
public class BeanDefinition {

    private String id;

    private String className;
    /**<property>标签**/
    private List<PropertyValue> propertyValues;
    /**<constructor>标签**/
    private List<PropertyValue> constructorValues;



    public BeanDefinition(){
        this.propertyValues = new ArrayList<>();
        this.constructorValues = new ArrayList<>();

    }

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

    public List<PropertyValue> getConstructorValues() {
        return constructorValues;
    }

    public BeanDefinition setConstructorValues(List<PropertyValue> constructorValues) {
        this.constructorValues = constructorValues;
        return this;
    }
}
