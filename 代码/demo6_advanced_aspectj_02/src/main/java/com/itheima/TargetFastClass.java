package com.itheima;


import org.springframework.cglib.core.Signature;
//MethodProxy.create()创建此对象
public class TargetFastClass {
    static Signature s0=new Signature("save","()V");
    static Signature s1=new Signature("save","(I)V");
    static Signature s2=new Signature("save","(J)V");
    //获取目标方法编号
    /*
    save()      0
    save(int)   1
    save(long)  2

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
            ((Target)target).save();
            return null;
        }if(index==1){
            ((Target)target).save((int)args[0]);
            return null;
        }if(index==2){
            ((Target)target).save((long)args[0]);
            return null;
        }
        throw new RuntimeException("没有此方法");
    }

    public static void main(String[] args) {
        TargetFastClass fastClass=new TargetFastClass();
        int index = fastClass.getIndex(new Signature("save", "(I)V"));
        fastClass.invoke(index,new Target(),new Object[]{1});
    }
}
