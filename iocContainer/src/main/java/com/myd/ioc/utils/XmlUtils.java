package com.myd.ioc.utils;

import com.myd.ioc.exception.XmlLabelNameError;

/**
 * @author myd
 * @date 2021/7/27  0:31
 */

public class XmlUtils {

    public static void checkNodeName(String nodeName){
        NodeName node = Enum.valueOf(NodeName.class,nodeName);
        switch(node){
            case beans:
            case bean:
            case property:
            case constructor:
            case propertyPlaceholder:
            case ComponentScan:
                        break;
            default:
                throw new XmlLabelNameError("nodeName:<"+nodeName+"> error, NodeName  is not defined ");
        }

    }




}
