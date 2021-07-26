package com.myd.ioc.parse;

import com.myd.ioc.beans.BeanDefinition;
import com.myd.ioc.beans.PropertyValue;
import com.myd.ioc.exception.PropertiesKeyNotFound;
import com.myd.ioc.exception.XmlLabelNameError;
import com.myd.ioc.utils.XmlUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author myd
 * @date 2021/7/24  0:54
 */

public class ParseXml {





    /**
     *
     * dom4j解析xml
     * @param path
     * @return
     */
    public static List<BeanDefinition> parseXml(String path) throws Exception {

        path = Thread.currentThread().getContextClassLoader().getResource(path).getPath();
        List<BeanDefinition> beanDefinitions = new ArrayList<>();//xml解析出来的bean存放在beanDefinitions
        SAXReader saxReader = new SAXReader();
        Document dom = saxReader.read(new File(path));
        Element root = dom.getRootElement();
        List<Element> elements = root.elements();
        Properties properties=null;
        for (Element element : elements) {
            String name = element.getName();//获取标签名；
            XmlUtils.checkNodeName(name);
            if(name.equals("bean")){//解析bean标签
                BeanDefinition beanDefinition = parseBean(element);
                beanDefinitions.add(beanDefinition);
            }else if(name.equals("propertyPlaceholder")){//加载properties文件
                 properties = parsePropertiesFile(element);
             }else if(name.equals("annotations")){

            }
        }

        replaceValue(properties,beanDefinitions);


        return beanDefinitions;

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
    public static BeanDefinition parseBean(Element bean){

        BeanDefinition beanDefinition = new BeanDefinition();
        String id = bean.attributeValue("id");
        String className = bean.attributeValue("class");
        List<PropertyValue> propertyValues = new ArrayList<>();
        List<Element> properties = bean.elements();
        for (Element property : properties) {//属性解析
            PropertyValue propertyValue = new PropertyValue();
            String name = property.getName();
            XmlUtils.checkNodeName(name);
            if(name.equals("constructor")){
                propertyValue.setConstructor(true);
            }else if(name.equals("property")){
                propertyValue.setConstructor(false);
            }else{
                throw new XmlLabelNameError("<"+name+" > cannot be used in <bean> tags");
            }
            parseBeanProperty(property,propertyValue);
            propertyValues.add(propertyValue);
        }
        beanDefinition.setId(id).setClassName(className).setPropertyValues(propertyValues);
        return beanDefinition;

    }

    /**
     * 解析bean的属性标签
     *
     * @param property
     * @param propertyValue
     */
    public static void parseBeanProperty(Element property,PropertyValue propertyValue){
        propertyValue.setPropertyName(property.attributeValue("name"));
        String value = property.attributeValue("value");
        if(value != null){
            propertyValue.setValue(value);
            propertyValue.setRef(false);
        }else{
            propertyValue.setValue(property.attributeValue("ref"));
            propertyValue.setRef(true);
        }
    }


    /**
     *
     * 解析标签：propertyPlaceholder
     * <propertyPlaceholder load="path"/>
     *
     * @param propertiesFile
     */
    public static Properties parsePropertiesFile(Element propertiesFile)throws IOException{
        String path = propertiesFile.attributeValue("load");
        return ParseProperty.parseProperty(path);
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
     * <bean id ="" class="">
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
    public static void replaceValue(Properties properties,List<BeanDefinition> beanDefinitions){
        if(properties.isEmpty() || beanDefinitions.isEmpty())return;
        for (BeanDefinition beanDefinition : beanDefinitions) {
            List<PropertyValue> propertyValues = beanDefinition.getPropertyValues();
            for (PropertyValue propertyValue : propertyValues) {
                String value = propertyValue.getValue().trim();
                if(value.matches("^(\\$\\{).+\\}$")){//截取key：${username} >  username-
                    String propertiesKey= value.substring(2,value.length()-1);
                    String propertiesValue = (String)properties.get(value);
                    if(propertiesValue == null){
                        throw new PropertiesKeyNotFound(beanDefinition.getClassName()+" property value :" +value+" not found in properties file");
                    }
                    propertyValue.setValue(propertiesValue);
                }
            }
        }
    }







}
