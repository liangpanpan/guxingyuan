package com.pp.sort;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/6/28       create this file
 * </pre>
 */
public class Second extends First {
    public void aMethod() {
        System.out.println("in Second class");
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        new Second();
    }
}

class First {
    public First() {
        aMethod();
    }

    public void aMethod() {
        System.out.println("in First class");
    }
}