package parse;

import com.myd.ioc.parse.ParseXml;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * @author myd
 * @date 2021/7/27  20:55
 */

public class TestParameters {


    public String gg;
    public String gg2;

    @Test
    public void testField(){
        Field[] fields = TestParameters.class.getFields();
        for (Field field : fields) {
            System.out.println(field.getType().getName());
            System.out.println(field.getName());
            System.out.println(field.toGenericString());
        }
    }

    @Test
    public void testArray(Integer[] a,boolean b){

        System.out.println(a.length);
        System.out.println(b);

    }
    @Test
    public void testTT() throws NoSuchMethodException {
        Method[] methods = TestParameters.class.getMethods();

        for (Method method : methods) {

            System.out.println("methodName:"+method.getName()+"; parameters:");

            Parameter[] parameters = method.getParameters();
            for (Parameter parameter : parameters) {
                System.out.println(parameter.getName());
            }


            Class<?>[] types = method.getParameterTypes();
            for (Class<?> type : types) {
                System.out.println(type.getSimpleName());
            }


        }



    }
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
