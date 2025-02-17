package com.itheima;

import org.springframework.cglib.core.Signature;
import org.springframework.cglib.reflect.FastClass;

public class ProxyFastClass {
    static Signature s0=new Signature("saveSuper","()V");
    static Signature s1=new Signature("saveSuper","(I)V");
    static Signature s2=new Signature("saveSuper","(J)V");
    //获取代理方法编号
    /*
    "saveSuper"()      0
    "saveSuper"(int)   1
    "saveSuper"(long)  2

    signature:包括方法名字、参数、返回值
     */
    public int getIndex(Signature signature){
        if(s0.equals(signature)){
            return 0;
        } else if (s1.equals(signature)) {
            return 1;
        } else if (s2.equals(signature)) {
            return 2;
        }
        return -1;
    }
    //根据方法编号，正确调用目标对象方法
    public Object invoke(int index,Object target,Object[] args){
        if(index==0){
            ((Proxy)target).saveSuper();
            return null;
        }if(index==1){
            ((Proxy)target).saveSuper((int)args[0]);
            return null;
        }if(index==2){
            ((Proxy)target).saveSuper((long)args[0]);
            return null;
        }
        throw new RuntimeException("没有此方法");
    }

    public static void main(String[] args) {
        ProxyFastClass fastClass=new ProxyFastClass();
        int index = fastClass.getIndex(new Signature("saveSuper", "(I)V"));
        fastClass.invoke(index,new Proxy(),new Object[]{1});
    }
}
