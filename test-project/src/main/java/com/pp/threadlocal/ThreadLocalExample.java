package com.pp.threadlocal;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/2/14       create this file
 * </pre>
 */
public class ThreadLocalExample {

    // 定义一个ThreadLocal变量，这里存储的是String类型
    public static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        // 创建两个线程并启动
        new Thread(() -> {
            // 设置当前线程的ThreadLocal变量
            threadLocal.set("Thread A");
            System.out.println("In Thread A: " + threadLocal.get());
        }).start();

        new Thread(() -> {
            // 设置当前线程的ThreadLocal变量，不影响其他线程
            threadLocal.set("Thread B");
            System.out.println("In Thread B: " + threadLocal.get());

            // 清理本线程的ThreadLocal变量
            threadLocal.remove();
            // 此时尝试获取已经移除的ThreadLocal变量，应该返回null
            System.out.println("After remove in Thread B: " + threadLocal.get());
        }).start();

        // 注意：主线程直接调用get()会报空指针异常，因为它没有设置过任何值
        // System.out.println("In Main Thread: " + threadLocal.get()); // 这行代码应避免运行，否则可能抛出NullPointerException
    }
}
