package com.myd.aop.filter;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author myd
 * @date 2021/8/5  1:08
 */

public class MethodFilter {

    private static final String methodNameRegex ="\\*?\\w*\\*?\\(.*\\)";
    private static final String modifierRegex = "(public|protected|private|\\*)";
    private static final String parametersRegex ;
    static {
        String param = "([A-Z]\\w*|int|double|float|char|byte|long|short|boolean)(\\[\\])*";//匹配：Boolean[][],int[],float...
        String allType = "\\.\\.";//..
        String noParam = "";//无参
        parametersRegex = "\\((" + allType + "|" + param + "(," + param + ")*|" + noParam + ")\\)";
    }

    /**
     *
     *  截取pointcut的访问修饰符
     *  execution(public qew.ert.dg.fgh.test())
     *  return public
     *
     * @param pointcut
     * @return
     */
    public static String modifier(String pointcut){
      return findRegex(pointcut,modifierRegex);
    }

    /**
     *
     * 截取pointcut的methodName
     *
     *
     * execution(public qew.ert.dg.fgh.test())
     * return test
     *
     * execution(public qew.ert.dg.fgh.*())
     * return *
     *
     * execution(public qew.ert.dg.fgh.test*())
     * return test*
     *
     * @param pointcut
     * @return
     */
    public static String methodNameRegex(String pointcut){
        String method=findRegex(pointcut.substring(10,pointcut.length()-1),methodNameRegex);
        int len = method.indexOf("(");
        return method.substring(0,len);
    }

    /**
     *  截取pointcut中参数列表
     *execution(public qew.ert.dg.fgh.test(..))
     * return {'..'}
     *
     * execution(public qew.ert.dg.fgh.test(A,E[],int))
     * return {'A','E[]','int'}
     *
     * execution(public qew.ert.dg.fgh.test())
     * return null;
     * @param pointcut
     * @return
     */
    public static String[]  parameterRegex(String pointcut){
        String paramsStr = findRegex(pointcut.substring(10,pointcut.length()-1),parametersRegex);
        if(paramsStr.equals("()"))return null;
        String[] params = paramsStr.replaceAll("(\\(|\\))", "").split(",");
        return params;
    }

    private static String findRegex(String pointcut,String regex){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(pointcut);
        if(matcher.find()){
            return matcher.group();
        }
        return null;
    }


    /**
     *
     * 匹配方法，是否被代理；
     *
     * @param method
     * @param pointcut
     * @return
     */
    public static boolean matchMethod(Method method,String pointcut){
        String modifierRegex = modifier(pointcut);
        String nameRegex =methodNameRegex(pointcut);
        String[] paramRegex = parameterRegex(pointcut);
        return matchModifier(modifierRegex,method) &&
                matchName(nameRegex,method) &&
                matchParamsType(paramRegex,method);

    }

    public static boolean matchParamsType(String[] paramRegex,Method method){
        if(paramRegex!= null && paramRegex[0].equals(".."))return true;
        Class<?>[] parameterTypes = method.getParameterTypes();//没有参数也会返回Class[]对象
        if(parameterTypes.length==0 && paramRegex == null)return true;
        if(parameterTypes.length==0 && paramRegex != null)return false;
        if(parameterTypes.length!=0 && paramRegex == null)return false;
        if(parameterTypes.length != paramRegex.length)return false;
        for (int i = 0; i < parameterTypes.length; i++) {//参数列表，严格按照顺序对比
            String type = parameterTypes[i].getSimpleName();
            System.out.println(type);
            if(!type.equals(paramRegex[i]))return false;
        }
        return true;
    }


    /**
     * Modifiers 访问修饰符
     *
     * 0：default(不添加访问修饰符)
     * 1：public
     * 2：private
     * 4：protected
     *
     *
     * @param modifierRegex
     * @param method
     * @return
     */
    public static boolean matchModifier(String modifierRegex,Method method){
        if(modifierRegex.equals("*"))return true;
        String modifier = null;

        switch (method.getModifiers()){
            case 0:
                modifier ="default";
                break;
            case 1:
                modifier ="public";
                break;
            case 2:
                modifier ="private";
                break;
            case 4:
                modifier ="protected";
                break;
        }
        return modifier.matches(modifierRegex);
    }

    public static boolean matchName(String nameRegex,Method method){
        nameRegex = nameRegex.replaceAll("\\*","\\\\w*");
        String name = method.getName();
        return name.matches(nameRegex);
    }






}
