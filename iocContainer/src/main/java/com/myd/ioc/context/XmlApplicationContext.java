package com.myd.ioc.context;

import com.myd.aop.BeanPostAfterInitProcessor;
import com.myd.aop.config.AspectConfig;
import com.myd.ioc.factory.DefaultBeanFactory;
import com.myd.ioc.parse.ParseXml;

/**
 * @author myd
 * @date 2021/7/27  21:13
 */

public class XmlApplicationContext implements ApplicationContext {


    private XmlConfiguration xmlConfiguration;

    private  DefaultBeanFactory defaultBeanFactory;

    private AnnotationApplicationContext annotationApplicationContext;

    public XmlApplicationContext(String configLocation) throws Exception {
        loadBeans(configLocation);
    }


    public void loadBeans(String configLocation) throws Exception {
        this.xmlConfiguration  = ParseXml.parseXml(configLocation);
        defaultBeanFactory = new DefaultBeanFactory(xmlConfiguration);
        if(xmlConfiguration.getAnnotationPackage()!= null){
            annotationApplicationContext = new AnnotationApplicationContext(xmlConfiguration.getAnnotationPackage(),xmlConfiguration);
        }
//        if(AspectConfig.hasAspectConfig()){
//            BeanPostAfterInitProcessor beanPostAfterInitProcessor = BeanPostAfterInitProcessor.getBeanPostAfterInitProcessor();
//            beanPostAfterInitProcessor.postProcessAllBean();
//        }




    }

    @Override
    public <T> T getBean(String id) {
        return (T)defaultBeanFactory.getBean(id);
    }

    @Override
    public <T> T getBean(Class classType) {
        return defaultBeanFactory.getBean(classType);
    }
}
