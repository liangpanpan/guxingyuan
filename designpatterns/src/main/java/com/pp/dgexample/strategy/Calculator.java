package com.pp.dgexample.strategy;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2022/1/17       create this file
 * </pre>
 */
public enum Calculator {
    //加法运算
    ADD("+") {
        public int exec(int a, int b) {
            return a + b;
        }
    },
    //减法运算
    SUB("-") {
        public int exec(int a, int b) {
            return a - b;
        }
    };
    String value = "";

    //定义成员值类型
    private Calculator(String _value) {
        this.value = _value;
    }

    //获得枚举成员的值
    public String getValue() {
        return this.value;
    }

    //声明一个抽象函数
    public abstract int exec(int a, int b);
}
