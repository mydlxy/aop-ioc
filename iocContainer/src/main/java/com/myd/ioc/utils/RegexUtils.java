package com.myd.ioc.utils;

import org.apache.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author myd
 * @date 2021/7/29  13:32
 */

public class RegexUtils {

  private static  Logger log = Logger.getLogger(RegexUtils.class);

    /**
     * 获取扫描路径
     * @param packageName
     * @return
     */
    public static String getScanPath(String packageName){
        if(packageName.contains("*")){
            throw new RuntimeException("扫描路径暂时不支持正则表达式。");//路径名称带有 * 直接GG。。
        }
        try {
            if(packageName.trim().length()==0)//项目根路径
                return RegexUtils.class.getResource("/").getPath();
            else
                return Thread.currentThread().getContextClassLoader().getResource(packageName.replaceAll("\\.","/")).getPath();
        }catch(NullPointerException e){
            e.printStackTrace();
            log.error("package:"+packageName+" error;");
            throw new NullPointerException();
        }

    }

    /**
     *
     * 获取给定package下的所有类名
     * @param packageName 包名
     * @return classNames
     */
    public static List<String> scanClassNames(String packageName){
        String path = getScanPath(packageName);
        List<String> classNames = new ArrayList<>();
        getClassNames(packageName,path,classNames);
        return classNames;
    }

    /**
     *
     * @param packageName 包名
     * @param path 绝对路径
     * @param classNames
     */
    public static void getClassNames(String packageName,String path,List<String> classNames){
        File file = new File(path);
        if(file.isDirectory()){
            String[] list = file.list();
            for (String s : list) {
                String sonPackageName = (packageName== null|| packageName.trim().length()==0)?s:packageName+"."+s;
                String sonClassPath = path+"/"+s;
                getClassNames(sonPackageName,sonClassPath,classNames);
            }
        }else{
            if(packageName.matches(".+(\\.class)$"))//匹配class文件
                classNames.add(packageName.substring(0,packageName.length()-6));//去掉类名后缀 '.class'
        }

    }











}
