package com.myd.ioc.parse.processNode;

import com.myd.ioc.beans.BeanDefinition;
import com.myd.ioc.beans.PropertyValue;
import com.myd.ioc.context.XmlConfiguration;
import com.myd.ioc.utils.XmlUtils;
import org.dom4j.Element;

import java.util.List;

/**
 * @author myd
 * @date 2021/8/19  16:11
 */

public class BeanNode implements XmlNode {
    private static volatile  BeanNode beanNode;
    private BeanNode(){}

    public static BeanNode instance(){
        if(beanNode == null){
            synchronized (BeanNode.class){
                if(beanNode == null)beanNode = new BeanNode();
            }
        }
        return beanNode;
    }

    @Override
    public void processNode(Element bean, XmlConfiguration xmlConfiguration) {

        BeanDefinition beanDefinition = new BeanDefinition();
        String id = XmlUtils.attributeValue(bean,"id");
        String className = XmlUtils.attributeValue(bean,"class");
        beanDefinition.setId(id).setClassName(className);
        List<Element> properties = bean.elements();
        for (Element property : properties) {//属性解析
            PropertyValue propertyValue = new PropertyValue();
            String name = property.getName();
            XmlUtils.checkBeanNodeName(name);
            if(name.equals("constructor")){
                parseBeanProperty(property,propertyValue,beanDefinition.getConstructorValues());
            }else{
                parseBeanProperty(property,propertyValue,beanDefinition.getPropertyValues());
            }

        }
        xmlConfiguration.addBeanDefinition(id,beanDefinition);

    }

    @Override
    public void processNode(Element element) {

    }


    /**
     * 解析bean的属性标签
     *<property name="" value=""/>
     *<property name ="" ref=""/>
     * @param property
     * @param propertyValue
     */
    public static void parseBeanProperty(Element property, PropertyValue propertyValue, List<PropertyValue> beanValues){
        String name = XmlUtils.attributeValue(property,"name");
        propertyValue.setPropertyName(name);
        String value = XmlUtils.attributeValue(property,"value");
        if(value != null){
            propertyValue.setValue(value).setRef(false);
        }else{
            propertyValue.setValue( XmlUtils.attributeValue(property,"ref")).setRef(true);
        }
        beanValues.add(propertyValue);
    }






}
