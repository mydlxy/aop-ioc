package com.myd.ioc.utils;
import com.myd.ioc.annotations.Value;
import com.myd.ioc.beans.PropertyValue;
import com.myd.ioc.exception.MatchConstructorError;
import com.myd.ioc.exception.PropertiesKeyNotFound;
import com.myd.ioc.exception.TypeConvertError;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author myd
 * @date 2021/7/26  19:07
 */

public class BeanUtils {


    /**
     *
     * 将bean中的 '${}' 引用替换成properties的值<br/>
     *
     * eg:<br/>
     * properties文件：<br/>
     * username=root<br/>
     *
     * 1.xml中bean引用了properties的username<br/>
     * <bean id ="" class="">
     *     <property name="name" value="${username}"/>
     *</bean>
     *
     * 该方法返回结果：<br/>
     * ${username} -> root
     *
     *
     * 2.使用注解@Value("${username}")
     *
     * @Value("${username}")
     * private String name;
     *
     * 将username的值注入到name;name = "root"
     *
     * @param properties
     * @param value
     */

    public static String replaceValue(Properties properties, String value){
        value = value.trim();
        if(value.matches("^(\\$\\{).+\\}$")){//截取key：${username} ->  username
            String propertiesKey= value.substring(2,value.length()-1);
            String propertiesValue = (String)properties.get(propertiesKey);
            if(propertiesValue == null){
                throw new PropertiesKeyNotFound(" property value :" +value+" not found in properties file");
            }
            return propertiesValue;
         }
        return value;
    }


    /**
     *
     * 在xml中读取的值类型都是String，
     *该方法 将String转换成属性值的类型；在生成bean时，转换成字段的属性；
     * @param value
     * @param type
     * @return
     */
    public static Object typeConvert(String value,String type){
        switch (type){
            case "String":
                return value;
            case "Integer":
                return Integer.valueOf(value);
            case "int":
                return Integer.parseInt(value);
            case "Boolean":
                return Boolean.valueOf(value);
            case "boolean":
                return Boolean.parseBoolean(value);
            case "Short":
                return Short.valueOf(value);
            case "short":
                return Short.parseShort(value);
            case "Long":
                return Long.valueOf(value);
            case "long":
                return Long.parseLong(value);
            case "Byte":
                return Byte.valueOf(value);
            case "byte":
                return Byte.parseByte(value);
            case "Character":
                return Character.valueOf(value.charAt(0));
            case "char":
                return value.charAt(0);
            case "Double":
                return Double.valueOf(value);
            case "double":
                return Double.parseDouble(value);
            case "Float":
                return Float.valueOf(value);
            case "float":
                return Float.parseFloat(value);
            default:
                throw  new TypeConvertError("不支持");
        }



    }

    /**
     *beanDefinition获取构造方法的参数名列表
     *
     * @param constructorParameters
     * @return
     */
    public static List<String> getConstructorParameterNames(List<PropertyValue> constructorParameters){
        List<String> parameterNames=new ArrayList<>();
        for (PropertyValue parameter : constructorParameters) {
            parameterNames.add(parameter.getPropertyName());
        }
        return parameterNames;
    }


    /**
     *
     * 根据 构造方法的参数名列表 匹配 对应的构造方法<br>
     * 只匹配public的构造方法;<br>
     * jdk1.8及以上可以获取方法参数名，在编译时添加参数: -parameters<br>
     *
     * 如果找到没有匹配的参数列表，报错；
     *
     * @param beanClass
     * @param constructorParameters
     * @return
     */
    public static Constructor getConstructor(Class beanClass, List<PropertyValue> constructorParameters){

        List<String> parameterNames = getConstructorParameterNames(constructorParameters);
        Constructor[] constructors = beanClass.getConstructors();
        for (Constructor constructor : constructors) {
            Parameter[] parameters = constructor.getParameters();

            boolean match = matchParameterNameList(parameters, parameterNames);
            if(match)return constructor;
        }
        throw new MatchConstructorError("create bean : "+beanClass.getName()+" no matching parameter list ");
    }


    /**
     *
     *
     * @param parameters
     * @param parameterNames
     * @return
     */
    public static boolean matchParameterNameList(Parameter[] parameters,List<String> parameterNames){
        if(parameterNames.size() != parameters.length)return false;
        for (Parameter parameter : parameters) {
            String name = parameter.getName();
            if(!parameterNames.contains(name))return false;
        }
        return true;
    }


    /**
     *
     * 检查属性是否有ref值
     * @param values
     * @return
     */
    public static boolean hasRef(List<PropertyValue> values){
        List<PropertyValue>  refs = new ArrayList<>();
        for (PropertyValue value : values) {
            if(value.isRef())return true;
        }
        return false;
    }


    /**
     *
     *
     * 构造函数的参数没有ref值，使用该方法返回构造函数的参数列表
     * @param parameters
     * @param values
     * @return
     */
    public static Object[] constructorValues(Parameter[] parameters,List<PropertyValue> values){
        Object[] initArgs = new Object[parameters.length];
        int i =0;
        for (Parameter parameter : parameters) {
            String name = parameter.getName();
            String type = parameter.getType().getSimpleName();
            for (PropertyValue value : values) {
                if(value.getPropertyName().equals(name)){
                    initArgs[i]=typeConvert(value.getValue(),type);
                    i++;
                    break;
                }

            }


        }

        return initArgs;

    }


    /**
     *
     * 首字母大写
     * @param str
     * @return
     */
    public static String UpperFirst(String str){
        return str.toUpperCase().substring(0,1)+str.substring(1);
    }


}
