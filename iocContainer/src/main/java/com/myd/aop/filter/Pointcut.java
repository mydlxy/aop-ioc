package com.myd.aop.filter;

import com.myd.aop.exception.PointcutError;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author myd
 * @date 2021/8/12  17:05
 */

public class Pointcut {

//    private static final String EXECUTION ="\\s*execution\\s*\\(\\s*(public|protected|private|\\*)\\s+(((\\*?\\w+\\*?|\\*)\\.)+\\.?)?(\\*?\\w+\\*?|\\*)\\(\\s*(\\.\\.|([A-Z]\\w*|int|double|float|char|byte|long|short|boolean)(\\[\\])*\\s*(,\\s*([A-Z]\\w*|int|double|float|char|byte|long|short|boolean)(\\[\\])*\\s*)*|)\\)\\s*\\)\\s*";
    private static final String EXECUTION;

    private static final String modifier;

    private static final String packagePath;

    private static final String methodNameRegex;

    private static final String methodParamList;

    private static final String methodSingleParamType;

    private static final String methodAllParamType;

    private static final String methodNoParam;
    static {
        modifier = "(public|protected|private|\\*)";
        packagePath = "(((\\*?\\w+\\*?|\\*)\\.)+\\.?)?";
        methodNameRegex = "(\\*?\\w+\\*?|\\*)";

        methodSingleParamType = "\\s*([A-Z]\\w*|int|double|float|char|byte|long|short|boolean)(\\[\\])*\\s*";
        methodAllParamType = "..";
        methodNoParam = "";
        methodParamList = "("+methodAllParamType+"|"+methodSingleParamType+"(,"+methodSingleParamType+")*|"+methodNoParam+")";

        EXECUTION = "\\s*execution\\s*\\(\\s*"+modifier+"\\s+"+packagePath+methodNameRegex+"\\("+methodParamList+"\\)\\s*\\)\\s*";
    }


    private final String pointcut;

    private final String methodName;

   public  Pointcut(String pointcut,String methodName){
       this.pointcut = getPointcut(pointcut);
       this.methodName = methodName;
   }


   public String getMethodName(){
       return methodName;
   }

    /**
     *
     * 检查pointcut写法是否正确
     * @param pointcut
     */
   public  void checkPoint(String pointcut){
       if(pointcut == null || pointcut.trim().equals(""))
           throw  new PointcutError("pointcut is null  error");
       if(!pointcut.matches(EXECUTION))
           throw  new PointcutError("pointcut:"+pointcut+"  error.");
   }

    /**
     *
     * 剪切掉表达式种多余空格；
     *
     * @param pointcut
     * @return
     */
    public String trim(String pointcut){
        String pointTrim = pointcut.replaceAll(" ","");
        Pattern pattern = Pattern.compile("(public|private|protected|\\*)");
        Matcher matcher = pattern.matcher(pointTrim);
        String regex=null ;
        if(matcher.find()){
            regex = matcher.group();
        }
        if(regex.equals("*")){
            regex="\\*";
        }
        String  replace = regex +"  ";
        return pointTrim.replaceFirst(regex,replace);
    }

    public String getPointcut(String pointcut){
        checkPoint(pointcut);
        return trim(pointcut);
    }

    public  boolean matchClass(Object target){
        return ClassFilter.matchClass(target,pointcut);
    }


    public  boolean matchMethod(Method method){
       return MethodFilter.matchMethod(method,pointcut);
    }


}
