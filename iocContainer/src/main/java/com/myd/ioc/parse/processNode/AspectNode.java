package com.myd.ioc.parse.processNode;

import com.myd.aop.config.AspectConfig;
import com.myd.ioc.context.XmlConfiguration;
import com.myd.ioc.utils.BeanUtils;
import com.myd.ioc.utils.XmlUtils;
import org.dom4j.Element;

import java.util.List;

/**
 * @author myd
 * @date 2021/8/19  16:20
 */

public class AspectNode implements XmlNode {


    private static volatile  AspectNode aspectNode;

    private AspectNode(){}

    public static AspectNode instance(){
        if(aspectNode == null){
            synchronized (AspectNode.class){
                if(aspectNode == null)aspectNode = new AspectNode();
            }
        }
        return aspectNode;
    }

    @Override
    public void processNode(Element aspect, XmlConfiguration xmlConfiguration) {
                processNode(aspect);
    }

    @Override
    public void processNode(Element aspect) {
        String id = XmlUtils.attributeValue(aspect,"id");
        AspectConfig aspectConfig = AspectConfig.getAspectConfig().setId(id);
        List<Element> elements = aspect.elements();
        for (Element element : elements) {
            String adviceType = element.getName().trim();
            XmlUtils.checkAspectNodeName(adviceType);
            String pointcut =XmlUtils.attributeValue(element,"pointcut");
            String methodName = XmlUtils.attributeValue(element,"method");
            aspectConfig.addAdvice(BeanUtils.UpperFirst(adviceType),pointcut,methodName);
        }

    }
}
