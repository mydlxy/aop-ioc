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
        if(id == null || id.length()==0)
            throw new NullPointerException("aspect id is null error...");
        AspectConfig aspectConfig = AspectConfig.getAspectConfig().setId(id);
        List<Element> elements = aspect.elements();
        for (Element element : elements) {
            String adviceType = element.getName().trim();
            XmlUtils.checkAspectNodeName(adviceType);
            String pointcut =XmlUtils.attributeValue(element,"pointcut");
            if(pointcut== null || pointcut.length()==0)
                throw new NullPointerException("aspect:"+id+","+adviceType+" pointcut is null error...");
            String methodName = XmlUtils.attributeValue(element,"method");
            if(methodName== null || methodName.length()==0)
                throw new NullPointerException("aspect:"+id+","+adviceType+" method is null error...");
            aspectConfig.addAdvice(BeanUtils.UpperFirst(adviceType),pointcut,methodName);
        }

    }
}
