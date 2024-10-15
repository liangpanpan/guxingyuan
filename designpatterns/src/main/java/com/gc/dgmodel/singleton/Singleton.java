package com.gc.dgmodel.singleton;

/**
 * 饿汉模式
 * @Title
 * @Description
 * @Author ppliang
 * @Date 2020/3/7
 */
public class Singleton {
    private static final Singleton singleton = new Singleton();

    //限制产生多个对象
    private Singleton() {
    }

    //通过该方法获得实例对象
    public static Singleton getSingleton() {
        return singleton;
    }

    //类中其他方法，尽量是static
    public void doSomething() {
        System.out.println("do some thing");
    }
}
