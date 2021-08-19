package com.myd.ioc.parse.processNode;

import com.myd.ioc.context.XmlConfiguration;
import com.myd.ioc.parse.ParseProperty;
import org.dom4j.Element;

import java.io.IOException;
import java.util.Properties;

/**
 * @author myd
 * @date 2021/8/19  16:21
 */

public class PropertyPlaceholderNode implements XmlNode {
    private static volatile  PropertyPlaceholderNode propertyPlaceholderNode;

    private PropertyPlaceholderNode(){}

    public static PropertyPlaceholderNode instance(){
        if(propertyPlaceholderNode == null){
            synchronized (PropertyPlaceholderNode.class){
                if(propertyPlaceholderNode == null)propertyPlaceholderNode = new PropertyPlaceholderNode();
            }
        }
        return propertyPlaceholderNode;
    }

    @Override
    public void processNode(Element propertiesFile, XmlConfiguration xmlConfiguration) throws IOException {
        String path = propertiesFile.attributeValue("load");
        xmlConfiguration.setPropertiesPath(path);
        Properties properties = ParseProperty.parseProperty(path);
        xmlConfiguration.setProperties(properties);

    }

    @Override
    public void processNode(Element element) {

    }
}
