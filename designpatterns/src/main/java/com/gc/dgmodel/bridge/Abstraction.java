package com.gc.dgmodel.bridge;

/**
 * 抽象化角色
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2022/1/20       create this file
 * </pre>
 */
public abstract class Abstraction {
    //定义对实现化角色的引用
    private Implementor imp;

    //约束子类必须实现该构造函数
    public Abstraction(Implementor _imp) {
        this.imp = _imp;
    }

    //自身的行为和属性
    public void request() {
        this.imp.doSomething();
    }

    //获得实现化角色
    public Implementor getImp() {
        return imp;
    }

}
