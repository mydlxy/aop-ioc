package com.myd.ioc.utils;

import com.myd.ioc.exception.XmlLabelNameError;
import org.dom4j.Element;

/**
 * @author myd
 * @date 2021/7/27  0:31
 */

public class XmlUtils {

    /**
     * com.myd.ioc.utils.NodeName：定义合法标签名
     * 检查标签名是否正确
     * @param nodeName
     */
    private static void checkNodeName(String nodeName){
        try {
            Enum.valueOf(NodeName.class, nodeName);
        }catch (IllegalArgumentException e){
            throw new XmlLabelNameError("不支持标签:<" + nodeName + "> ,在枚举类： NodeName 中定义了xml支持的标签格式。");
        }
    }

    /**
     *
     * aspect的子标签名检查
     *
     * @param nodeName
     */
    public static void checkAspectNodeName(String nodeName){
        try {
            NodeName node = Enum.valueOf(NodeName.class, nodeName);
            switch (node) {
                case after:
                case before:
                case afterReturning:
                case afterThrowable:
                case around:
                    break;
                default:
                    throw new XmlLabelNameError("标签:<" + nodeName + "> ,不是<aspect>的子标签");
            }
        }catch(IllegalArgumentException e){
            throw new XmlLabelNameError("不支持标签:<" + nodeName + "> ,在枚举类： NodeName 中定义了xml支持的标签格式。");
        }
    }


    public static void checkBeanNodeName(String nodeName){
        try{
            NodeName node =  Enum.valueOf(NodeName.class,nodeName);
            switch (node){
                case constructor:
                case property:
                    break;
                default:
                    throw new XmlLabelNameError("标签:<" + nodeName + "> ,不是<bean>的子标签");
            }
        }catch (IllegalArgumentException e){
            throw new XmlLabelNameError("不支持标签:<" + nodeName + "> ,在枚举类： NodeName 中定义了xml支持的标签格式。");
        }

    }




    public static void checkRootNodeName(String nodeName){
        try {
            NodeName node = Enum.valueOf(NodeName.class, nodeName);
            switch (node) {
                case bean:
                case propertyPlaceholder:
                case aspect:
                case componentScan:
                    break;
                default:
                    throw new XmlLabelNameError("标签:<" + nodeName + "> ,不是<beans>的子标签。");
            }
        }catch(IllegalArgumentException e){
            throw new XmlLabelNameError("不支持标签:<" + nodeName + "> ,在枚举类： NodeName 中定义了xml支持的标签格式。");
        }
    }


    public static String attributeValue(Element element,String attribute){
        String value = element.attributeValue(attribute);
        if(value == null || value.trim().length() == 0)
            throw new NullPointerException("XML NODE:"+element.getName()+"'s  attribute :"+attribute +" value is null.");
        return  value.trim();
    }




}
