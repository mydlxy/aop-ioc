package com.myd.ioc.parse;

import com.myd.ioc.context.XmlConfiguration;
import com.myd.ioc.beans.BeanDefinition;
import com.myd.ioc.beans.PropertyValue;
import com.myd.ioc.exception.RefNotFoundError;
import com.myd.ioc.exception.XmlLabelNameError;
import com.myd.ioc.utils.BeanUtils;
import com.myd.ioc.utils.XmlUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import java.io.File;
import java.io.IOException;
import java.util.*;

import  org.apache.log4j.Logger;

/**
 * @author myd
 * @date 2021/7/24  0:54
 */

public class ParseXml {

private static Logger log  = Logger.getLogger(ParseXml.class);

    /**
     *
     * dom4j解析xml
     * @param path
     * @return
     */
    public static XmlConfiguration parseXml(String path) throws Exception {

        log.info("parse xml start.....");
        String configLocation = Thread.currentThread().getContextClassLoader().getResource(path).getPath();
        XmlConfiguration xmlConfiguration = XmlConfiguration.getInstance().setConfigLocation(configLocation);
        SAXReader saxReader = new SAXReader();
        Document dom = saxReader.read(new File(configLocation));
        Element root = dom.getRootElement();
        List<Element> elements = root.elements();
        for (Element element : elements) {
            String name = element.getName();//获取标签名；
            XmlUtils.checkNodeName(name);
            if(name.equals("bean")){//解析bean标签
                parseBean(element, xmlConfiguration);
            }else if(name.equals("propertyPlaceholder")){//加载properties文件
                parsePropertiesFile(element, xmlConfiguration);
             }else if(name.equals("ComponentScan")){
                //后续添加annotation解析;
                xmlConfiguration.setAnnotationPackage(element.attributeValue("package"));
            }
        }
        log.info("parse xml has end.....");
        //将 '${}'替换成值
        replaceBeanDefinitionValue(xmlConfiguration.getProperties(), xmlConfiguration.getBeanDefinitions().values());

        //检查bean属性引用的ref值是否正确
        checkBeanPropertyRef(xmlConfiguration);


        return xmlConfiguration;

    }

    /**
     * 解析bean标签
     *
     * <bean id="" class="">
     *  <constructor name="" value=""/>
     *  <constructor name="" ref=""/>
     *  <property name="" value=""/>
     *  <property name="" ref=""/>
     * </bean>
     * @param bean
     * @return
     */
    public static void parseBean(Element bean, XmlConfiguration xmlConfiguration){

        BeanDefinition beanDefinition = new BeanDefinition();
        String id = bean.attributeValue("id");
        String className = bean.attributeValue("class");
        beanDefinition.setId(id).setClassName(className);
        List<Element> properties = bean.elements();
        for (Element property : properties) {//属性解析
            PropertyValue propertyValue = new PropertyValue();
            String name = property.getName();
            XmlUtils.checkNodeName(name);
            if(name.equals("constructor")){
                parseBeanProperty(property,propertyValue,beanDefinition.getConstructorValues());
            }else if(name.equals("property")){
                parseBeanProperty(property,propertyValue,beanDefinition.getPropertyValues());
            }else{
                throw new XmlLabelNameError("<"+name+" > cannot be used in <bean> tags");
            }

        }
        xmlConfiguration.addBeanDefinition(id,beanDefinition);
    }

    /**
     * 解析bean的属性标签
     *<property name="" value=""/>
     *<property name ="" ref=""/>
     * @param property
     * @param propertyValue
     */
    public static void parseBeanProperty(Element property,PropertyValue propertyValue,List<PropertyValue> beanValues){
        String name = property.attributeValue("name");
//        if(name == null || name.trim().equals(""))
//            throw new XmlAttributeNullPointerError();
        propertyValue.setPropertyName(property.attributeValue("name"));

        String value = property.attributeValue("value");
        if(value != null){
            propertyValue.setValue(value).setRef(false);
        }else{
            propertyValue.setValue(property.attributeValue("ref")).setRef(true);
        }
        beanValues.add(propertyValue);
    }


    /**
     *
     * 解析标签：propertyPlaceholder
     * <propertyPlaceholder load="path"/>
     *
     * @param propertiesFile
     */
    public static void parsePropertiesFile(Element propertiesFile, XmlConfiguration xmlConfiguration)throws IOException{
        String path = propertiesFile.attributeValue("load");
        xmlConfiguration.setPropertiesPath(path);
        Properties properties = ParseProperty.parseProperty(path);
        xmlConfiguration.setProperties(properties);
    }


    /**
     *
     * 将bean中的 '${}' 引用替换成properties的值<br/>
     *
     * eg:<br/>
     * properties文件：<br/>
     * username=root<br/>
     *
     * xml中bean引用了properties的username<br/>
     * <bean>
     *     <property name="name" value="${username}"/>
     *</bean>
     *
     * 该方法返回结果：<br/>
     * ${username} -> root
     *
     *
     * @param properties properties文件中定义的key-value键值对
     * @param beanDefinitions 解析xml文件中bean信息
     */
    public static void replaceBeanDefinitionValue(Properties properties,Collection<BeanDefinition> beanDefinitions){
        if(properties == null || properties.isEmpty() || beanDefinitions.isEmpty())return;
        for (BeanDefinition beanDefinition : beanDefinitions) {
            List<PropertyValue> propertyValues = beanDefinition.getPropertyValues();
            List<PropertyValue> constructorValues = beanDefinition.getConstructorValues();
            replaceValues(properties,propertyValues);
            replaceValues(properties,constructorValues);
        }
    }

    public static void replaceValues(Properties properties,List<PropertyValue> values){
        for (PropertyValue propertyValue: values) {
            String value = BeanUtils.replaceValue(properties, propertyValue.getValue().trim());
            propertyValue.setValue(value);
        }

    }


    /**
     * 检查beanDefinition 的ref引用值是否正确；
     * @param xmlConfiguration
     */
    public static void checkBeanPropertyRef(XmlConfiguration xmlConfiguration){
        Map<String, BeanDefinition> beanDefinitions = xmlConfiguration.getBeanDefinitions();
        if(beanDefinitions.isEmpty())return;
        Set<String> refs = beanDefinitions.keySet();//id值
        Collection<BeanDefinition> beans = beanDefinitions.values();
        for (BeanDefinition bean : beans) {
            checkBeanPropertyRef(bean,refs);
        }
    }

    public static void checkBeanPropertyRef(BeanDefinition beanDefinition,Set<String> refs ){
        List<PropertyValue> propertyValues = beanDefinition.getPropertyValues();
        List<PropertyValue> constructorValues = beanDefinition.getConstructorValues();
        List<PropertyValue> allPropertyValues = new ArrayList<>();
//        propertyValues.addAll(constructorValues);//合并2个list
        allPropertyValues.addAll(propertyValues);
        allPropertyValues.addAll(constructorValues);
        for (PropertyValue propertyValue : allPropertyValues) {
            boolean ref = propertyValue.isRef();
            if(ref){
                String refValue = propertyValue.getValue();
                if(!refs.contains(refValue))throw new RefNotFoundError(beanDefinition.getClassName()+" ref =" +refValue+" xml not found error.");
            }

        }



    }







}
