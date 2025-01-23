package com.pp.test;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/1/12       create this file
 * </pre>
 */
public class PrintTest {

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {

        Class<?> aClass = Class.forName("com.pp.test.StaticTest");
        Object o = aClass.newInstance();
        StaticTest test = (StaticTest) o;
        System.out.println(test.getName());

        System.out.println(StaticTest.A);
        System.out.println("111222333");
        System.out.println(StaticTest.NAME);

    }
}
