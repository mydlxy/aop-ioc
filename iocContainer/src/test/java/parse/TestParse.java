package parse;

import com.myd.ioc.parse.ParseProperty;
import com.myd.ioc.parse.ParseXml;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Iterator;
import java.util.Properties;

/**
 * @author myd
 * @date 2021/7/27  1:55
 */

public class TestParse {


    @Test
    public void test(){
        try {
            Properties properties = ParseProperty.parseProperty("rr.properties");
            Iterator<Object> iterator = properties.keySet().iterator();
            while(iterator.hasNext()){
                Object key = iterator.next();
                Object value = properties.get(key);
                System.out.println("key:" +key+";value:"+value);
              }
         } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testStr(){

        String y =" ${34qwer} ";
        y =y.trim();
        boolean t = y.matches("^(\\$\\{.+\\}$)");
        System.out.println(t);
        System.out.println(y.substring(2,y.length()-1)+";hh");
    }

    @Test
    public void testReflct(){

        Method[] methods = TestParse.class.getMethods();
        for (Method method : methods) {

            String name = method.getName();
            System.out.println(name);
            Parameter[] parameters = method.getParameters();
            for (Parameter parameter : parameters) {
                System.out.println(parameter.getName());
            }



        }


    }



}
