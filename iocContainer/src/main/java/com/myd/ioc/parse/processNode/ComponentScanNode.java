package com.myd.ioc.parse.processNode;

import com.myd.ioc.context.XmlConfiguration;
import org.dom4j.Element;

/**
 * @author myd
 * @date 2021/8/19  16:23
 */

public class ComponentScanNode implements XmlNode {
    private static volatile  ComponentScanNode componentScanNode;

    private ComponentScanNode(){}

    public static ComponentScanNode instance(){
        if(componentScanNode == null){
            synchronized (ComponentScanNode.class){
                if(componentScanNode == null)componentScanNode = new ComponentScanNode();
            }
        }
        return componentScanNode;
    }

    @Override
    public void processNode(Element element, XmlConfiguration xmlConfiguration) {
        xmlConfiguration.setAnnotationPackage(element.attributeValue("package"));
    }

    @Override
    public void processNode(Element element) {

    }
}
