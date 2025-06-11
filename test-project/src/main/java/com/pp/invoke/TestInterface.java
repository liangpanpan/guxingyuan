package com.pp.invoke;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/6/11       create this file
 * </pre>
 */
public class TestInterface implements ITestInterface{

    protected String print(String message) {
        System.out.println("message:" + message);
        return "result " + message;
    }
}
