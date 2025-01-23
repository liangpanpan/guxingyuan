package com.pp.test;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/1/12       create this file
 * </pre>
 */
public class StaticTest {

    public static final String A = "AAAA";

    public static String NAME = "123";

    static {
        System.out.println("StaticTest init");
        NAME = "111";
    }

    public String getName() {
        return NAME;
    }


}
