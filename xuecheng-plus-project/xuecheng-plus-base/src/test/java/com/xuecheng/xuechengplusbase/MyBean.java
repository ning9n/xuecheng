package com.xuecheng.xuechengplusbase;


import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class MyBean implements InitializingBean, DisposableBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(this+"初始化");
    }

    @PostConstruct
    public void init(){
        System.out.println(this+"PostConstruct初始化");
    }
    public void init1(){
        System.out.println(this+"初始化");
    }
    public void destroy1(){
    }
    @PreDestroy
    public void destroy2(){
    }
    @Override
    public void destroy() throws Exception {
    }
    @Autowired
    private ApplicationContext context;
    public MyBean getMyBean(){
        return context.getBean(MyBean.class);
    }
}
