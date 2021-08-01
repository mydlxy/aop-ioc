package com.myd.ioc.context;

import com.myd.ioc.beans.BeanDefinition;
import com.myd.ioc.exception.DuplicateException;

import java.util.*;

/**
 * @author myd
 * @date 2021/7/27  12:14
 */

/**
 *
 * xml中读取的信息
 * 一个xml对应一个Configuration
 */
public class XmlConfiguration {


    private Map<String,BeanDefinition> beanDefinitions;
    /* xml 路径**/
    private String configLocation ;
    /*扫描注解路径**/
    private String annotationPackage;
    /*解析的properties文件对象 */
    private Properties properties;
    /*properties文件路径*/
    private String propertiesPath;

    private static volatile XmlConfiguration single;

    private XmlConfiguration() {
        this.beanDefinitions = new HashMap<>();
    }
    public void addBeanDefinition(String id,BeanDefinition beanDefinition){
        if(beanDefinitions.containsKey(id))
            throw new DuplicateException("id:"+id+" already exists");
        this.beanDefinitions.put(id,beanDefinition);
    }

    public static XmlConfiguration getInstance(){
        if(single == null){
            synchronized (XmlConfiguration.class){
                if(single == null){
                    single = new XmlConfiguration();
                }
            }
        }
        return single;
    }



    public String getConfigLocation() {
        return configLocation;
    }

    public XmlConfiguration setConfigLocation(String configLocation) {
        this.configLocation = configLocation;
        return this;
    }

    public Map<String,BeanDefinition> getBeanDefinitions() {
        return beanDefinitions;
    }

    public XmlConfiguration setBeanDefinitions(Map<String,BeanDefinition> beanDefinitions) {
        this.beanDefinitions = beanDefinitions;
        return this;
    }

    public String getAnnotationPackage() {
        return annotationPackage;
    }

    public XmlConfiguration setAnnotationPackage(String annotationPackage) {
        this.annotationPackage = annotationPackage;
        return this;
    }

    public Properties getProperties() {
        return properties;
    }

    public XmlConfiguration setProperties(Properties properties) {
        this.properties = properties;
        return this;
    }

    public String getPropertiesPath() {
        return propertiesPath;
    }

    public XmlConfiguration setPropertiesPath(String propertiesPath) {
        this.propertiesPath = propertiesPath;
        return this;
    }
}
