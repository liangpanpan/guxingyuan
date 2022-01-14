package com.pp.dgexample.command;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2022/1/14       create this file
 * </pre>
 */
public class RequirementGroup extends Group {
    /**
     * 客户要求需求组过去和他们谈
     */
    @Override
    public void find() {
        System.out.println("找到需求组...");
    }

    /**
     * 客户要求增加一项需求
     */
    @Override
    public void add() {
        System.out.println("客户要求增加一项需求...");
    }


    //客户要求修改一项需求
    @Override
    public void change() {
        System.out.println("客户要求修改一项需求...");
    }

    //客户要求删除一项需求
    @Override
    public void delete() {
        System.out.println("客户要求删除一项需求...");
    }

    //客户要求给出变更计划
    @Override
    public void plan() {
        System.out.println("客户要求需求变更计划...");
    }

}
