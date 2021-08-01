package com.myd.ioc.context;

import com.myd.ioc.factory.AnnotationBeanFactory;

/**
 * @author myd
 * @date 2021/7/30  16:46
 */

public class AnnotationApplicationContext implements ApplicationContext {

    private AnnotationBeanFactory annotationBeanFactory;
    public AnnotationApplicationContext(String scanPackage, XmlConfiguration xmlConfiguration) throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        this.annotationBeanFactory = new AnnotationBeanFactory(scanPackage, xmlConfiguration);
    }


    @Override
    public <T> T getBean(String id) {
        return (T)annotationBeanFactory.getBean(id);
    }

    @Override
    public <T> T getBean(Class classType) {
        return annotationBeanFactory.getBean(classType);
    }
}
