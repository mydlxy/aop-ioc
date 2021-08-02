package parse;

import com.myd.ioc.parse.ParseXml;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * @author myd
 * @date 2021/7/27  20:55
 */

public class TestParameters {


    @Test
    public void getMeterName(){

        /**
         * 在编译时添加参数 -parameters(保留参数名)
         */
        Class<ParseXml> parseXmlClass = ParseXml.class;

        Method[] methods = parseXmlClass.getMethods();
        for (Method method : methods) {
            Parameter[] parameters = method.getParameters();
            System.out.println("\n*************methodName:"+method.getName()+"**************");
            for (Parameter parameter : parameters) {
                System.out.print(parameter.getName()+"\t");
            }
            System.out.println("\n*************methodName:"+method.getName()+"**************");

        }


    }





}
