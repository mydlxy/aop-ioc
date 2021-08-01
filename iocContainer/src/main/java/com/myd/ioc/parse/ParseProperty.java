package com.myd.ioc.parse;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * @author myd
 * @date 2021/7/27  0:16
 */

/**
 *
 *
 * 解析properties文件
 */
public class ParseProperty {

    private static Logger log =  Logger.getLogger(ParseProperty.class);
    public static Properties parseProperty(String path)throws IOException  {
        String  propertyPath = Thread.currentThread().getContextClassLoader().getResource(path).getPath();
        log.debug("properties file path:"+propertyPath);
        Properties properties = new Properties();
        FileInputStream fis =null;
        try{
             fis = new FileInputStream(propertyPath);
             properties.load(fis);
             log.debug("properties parse complete...");
        }finally {
            if(fis!= null) {
              fis.close();
            }
        }
        return properties;
    }


}
