package com.myd.aop.filter;

/**
 * @author myd
 * @date 2021/8/3  23:08
 */


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * 过滤需要aop的class
 */
public class ClassFilter {

    private static final String packageRegex = "((\\*?\\w+\\*?|\\*)\\.)+\\.?";

    /**
     *
     * 返回表达式中包名部分
     * execution(public qew.ert.dg.fgh.test())
     *return  qew.ert.dg.fgh
     *
     *
     * execution(public test())
     * return null
     *
     * execution(public *.test())
     * return *
     *
     * @param pointcut
     * @return
     */
    public static String packageRegex(String pointcut){
        Pattern pattern = Pattern.compile(packageRegex);
        Matcher matcher = pattern.matcher(pointcut);
        if(matcher.find()){
            String value = matcher.group();
            return value.substring(0,value.length()-1);
        }
        return null;
    }

    /**
     *
     * 检查bean是否需要做动态代理aop
     *
     * @param bean
     * @param pointcut
     * @return
     */
    public static boolean matchClass(Object bean,String pointcut){
       String regex = packageRegex(pointcut);
        if( regex == null || regex.equals("*"))
            return true;
        String regexClass = regexClass(regex);
        String beanName = bean.getClass().getName();
        return beanName.matches(regexClass);
    }

    /**
     *
     * 将从pointcut中获取的包正则表达式转换成能匹配class的正则表达式；
     *
     * e.g.
     * qew.ert.dg.fgh  :匹配qew.ert.dg.fgh包下的所有类:A,B,C
     * qew.ert.dg.fgh.A
     * qew.ert.dg.fgh.B
     * qew.ert.dg.fgh.C
     *
     *  qew.ert.dg.fgh，这个式子本身不能匹配A,B,C的类名；因此需要转换这个式子
     *  qew.ert.dg.fgh -> qew.ert.dg.fgh\\.\\w+
     * 考虑到qew.ert.dg.fgh.A 直接写到类名，因此改进一下：qew.ert.dg.fgh(\\.\\w+)?
     * @param regex
     * @return
     */
    public static String regexClass(String regex){
        regex  =regex.replaceAll("\\*","\\\\w*").replaceAll("\\.","\\\\.");
        if(regex.endsWith(".")){
            return regex+".*";
        }else if(regex.endsWith("\\.\\w*")){
           return regex;
        }
        return regex+"(\\.\\w+)?";
    }



}
