package com.gc.dgmodel.abstractfactory;

/**
 * @Title
 * @Description
 * @Author ppliang
 * @Date 2020/3/7
 */
public abstract class AbstractProductB {
    //每个产品共有的方法
    public void shareMethod() {
        System.out.println("AbstractProductB shareMethod");
    }

    //每个产品相同方法，不同实现
    public abstract void doSomething();
}
