package com.myd.ioc.exception;

/**
 * @author myd
 * @date 2021/7/27  0:09
 */

/**
 *
 * 加载bean时，id 重复报错
 *
 *
 */
public class DuplicateException extends RuntimeException {


   public DuplicateException(String message){
       super(message);
   }

   public DuplicateException(){}

   public DuplicateException(String message, Throwable cause){
       super(message,cause);
   }

   public DuplicateException(Throwable cause){
       super(cause);
    }

}
