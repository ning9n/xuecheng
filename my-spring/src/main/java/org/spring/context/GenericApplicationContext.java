package org.spring.context;

import org.spring.beanfactory.BeanFactory;
import org.spring.beanfactory.DefaultListenableBeanFactory;

import java.util.concurrent.atomic.AtomicBoolean;

public class GenericApplicationContext implements ConfigurableApplicationContext{
    private final DefaultListenableBeanFactory beanFactory;
    private final AtomicBoolean refreshed;


    public GenericApplicationContext(DefaultListenableBeanFactory beanFactory, AtomicBoolean refreshed) {
        this.beanFactory = beanFactory;
        this.refreshed = refreshed;
    }

    public GenericApplicationContext() {
        beanFactory=new DefaultListenableBeanFactory();
        refreshed=new AtomicBoolean();
    }

    @Override
    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    @Override
    public void refresh() {

    }
    //相比Class<?>提供了更好的类型安全性和编译时检查，可以减少运行时错误
    public <T> void registerBean(Class<T> beanClass){

    }
}
