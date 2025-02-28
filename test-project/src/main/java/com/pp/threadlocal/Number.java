package com.pp.threadlocal;

/**
 * <pre>
 * @Version 1.0
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2020/7/2       create this file
 * </pre>
 */
public class Number {

    private int value = 0;

    public Number add() {
        this.value = value + 1;
        return this;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Number{" +
                "value=" + value +
                '}';
    }
}
