package com.myd.ioc.utils;

import com.myd.ioc.exception.XmlLabelNameError;

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
    public static void checkNodeName(String nodeName){
        try {
            Enum.valueOf(NodeName.class, nodeName);
        }catch (IllegalArgumentException e){
            throw new XmlLabelNameError("不支持标签:<" + nodeName + "> ,在枚举类： NodeName 中定义了xml支持的标签格式。");

        }

    }





}
