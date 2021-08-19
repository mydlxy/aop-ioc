package com.myd.ioc.parse.processNode;

import com.myd.ioc.annotations.Bean;
import com.myd.ioc.exception.XmlLabelNameError;
import com.myd.ioc.utils.NodeName;

/**
 * @author myd
 * @date 2021/8/19  16:09
 */

public class NodeFactory {

   public static XmlNode create(String nodeName){
       NodeName type = Enum.valueOf(NodeName.class,nodeName);
       switch (type){
           case bean:                return BeanNode.instance();
           case propertyPlaceholder: return PropertyPlaceholderNode.instance();
           case componentScan:       return ComponentScanNode.instance();
           case aspect:              return AspectNode.instance();
           default:
               throw new XmlLabelNameError("不支持处理标签："+nodeName);
       }

    }

}
