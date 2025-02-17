package com.tiktok.base.exception;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description 通用错误信息
 * @author Mr.M
 * @date 2022/9/6 11:29
 * @version 1.0
 */
public enum CommonError {

   UNKNOWN_ERROR("执行过程异常，请重试。"),
   PARAMS_ERROR("非法参数"),
   OBJECT_NULL("对象为空"),
   QUERY_NULL("查询结果为空"),
   REQUEST_NULL("请求参数为空");

   private String errMessage;

   public String getErrMessage() {
      return errMessage;
   }

   private CommonError( String errMessage) {
      this.errMessage = errMessage;
      ReentrantLock reentrantLock=new ReentrantLock();
      //创建新的条件变量
      Condition condition1 = reentrantLock.newCondition();
      Condition condition2 = reentrantLock.newCondition();
      reentrantLock.lock();
      //进入等待
      try {
         condition1.awaitNanos(2);
      } catch (InterruptedException e) {
         throw new RuntimeException(e);
      }
      //唤醒
      condition1.signal();
      condition1.signalAll();
   }

}