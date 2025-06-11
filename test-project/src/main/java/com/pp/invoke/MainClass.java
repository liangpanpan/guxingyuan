package com.pp.invoke;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/6/11       create this file
 * </pre>
 */
public class MainClass {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ITestInterface  testInterface = new TestInterface();
        Class<? extends ITestInterface> aClass = testInterface.getClass();

        // 获得该类所有的方法（共有、私有）
        // getMethod 获得该类以及父类的所有共有方法
        Method print = aClass.getDeclaredMethod("print", String.class);

        print.setAccessible(true);

        Object helloWorld = print.invoke(testInterface, "hello world");
        System.out.println(helloWorld);

    }


}
