package com.gc.dgmodel.visitor;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2022/1/18       create this file
 * </pre>
 */
public class Visitor implements IVisitor {
    //访问el1元素
    public void visit(ConcreteElement1 el1) {
        el1.doSomething();
    }

    //访问el2元素
    public void visit(ConcreteElement2 el2) {
        el2.doSomething();
    }

}
