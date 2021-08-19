package com.myd.ioc.parse.processNode;

import com.myd.ioc.context.XmlConfiguration;
import org.dom4j.Element;

/**
 * @author myd
 * @date 2021/8/19  16:11
 */

public interface XmlNode {

    void processNode(Element element, XmlConfiguration xmlConfiguration)throws Exception;
    void processNode(Element element);
}
