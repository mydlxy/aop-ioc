package com.myd.ioc.parse;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * @author myd
 * @date 2021/7/27  0:16
 */

public class ParseProperty {


    public static Properties parseProperty(String path)throws IOException  {
        path = Thread.currentThread().getContextClassLoader().getResource(path).getPath();
        System.out.println(path);
        Properties properties = new Properties();
        FileInputStream fis =null;
        try{
             fis = new FileInputStream(path);
             properties.load(fis);
        }finally {
            if(fis!= null) {
              fis.close();
            }
        }
        return properties;
    }


}
