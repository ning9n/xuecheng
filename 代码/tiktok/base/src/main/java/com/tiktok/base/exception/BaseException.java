package com.tiktok.base.exception;


/**
 * @description 学成在线项目异常类
 * @author Mr.M
 * @date 2022/9/6 11:29
 * @version 1.0
 */
public class BaseException extends RuntimeException {

   private String errMessage;

   public BaseException() {
      super();
   }

   public BaseException(String errMessage) {
      super(errMessage);
      this.errMessage = errMessage;
   }

   public String getErrMessage() {
      return errMessage;
   }

   public static void cast(CommonError commonError){
       throw new BaseException(commonError.getErrMessage());
   }
   public static void cast(String errMessage){
       throw new BaseException(errMessage);
   }

}