package com.pp.dgexample.bridge;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2022/1/20       create this file
 * </pre>
 */
public class House extends Product {
    //豆腐渣就豆腐渣呗，好歹也是房子
    public void beProducted() {
        System.out.println("生产出的房子是这样的...");
    }

    //虽然是豆腐渣，也是能够销售出去的
    public void beSelled() {
        System.out.println("生产出的房子卖出去了...");
    }
}
