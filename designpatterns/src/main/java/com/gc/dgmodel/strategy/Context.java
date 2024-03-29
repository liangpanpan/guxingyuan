package com.gc.dgmodel.strategy;

/**
 * 封装角色
 * 策略模式的重点就是封装角色，它是借用了代理模式的思路，大家可以想想，它和代理模式有什么差别，
 * 差别就是策略模式的封装角色和被封装的策略类不用是同一个接口，如果是同一个接口那就成为了代理模式。
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2022/1/17       create this file
 * </pre>
 */
public class Context {
    // 抽象策略
    private Strategy strategy = null;

    //构造函数设置具体策略
    public Context(Strategy _strategy) {
        this.strategy = _strategy;
    }

    //封装后的策略方法
    public void doAnything() {
        this.strategy.doSomething();
    }
}
