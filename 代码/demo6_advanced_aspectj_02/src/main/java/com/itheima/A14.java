package com.itheima;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class A14 {
    public static void main(String[] s) {
        Proxy proxy=new Proxy();
        Target target=new Target();
        proxy.setMethodInterceptor(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                System.out.println("before");
                //反射调用
                //return method.invoke(target,args);
                //非反射调用
                //return methodProxy.invoke(target,args);//结合目标使用
                System.out.println(methodProxy);
                return methodProxy.invokeSuper(o,args);//结合目标使用
            }
        });
        System.out.println(proxy);
        proxy.save();
        proxy.save(1);
        proxy.save(2L);
    }
}
