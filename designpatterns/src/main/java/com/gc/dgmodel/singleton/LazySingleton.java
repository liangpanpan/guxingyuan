package com.gc.dgmodel.singleton;

/**
 * 懒汉模式
 * 比较懒需要的时候才创建，所以叫懒汉模式
 * 1. instance声明为volatile，这是为了确保当instance被初始化成Singleton实例之后，其他线程能够立即看到这个变化，同时也能防止指令重排序带来的问题。
 * 2. getInstance()方法中首先进行非空检查，如果instance不为null，则直接返回，避免了每次调用都进行同步操作的开销
 * 3. 只有当instance为null时，才会进入同步块，这是第二次检查，确保在只有一个线程能够执行到创建实例的代码，从而保证了线程安全。
 * 4. 使用Singleton.class作为同步锁，这是因为类加载器保证了每个类只加载一次，因此对应的.class对象在JVM中也是唯一的，可以安全地作为锁对象。
 *
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2024/10/15       create this file
 * </pre>
 */
public class LazySingleton {

    // 使用volatile关键字确保多线程环境下的可见性和禁止指令重排序
    private static volatile LazySingleton instance;

    // 私有，避免外部创建
    private LazySingleton() {
    }

    /**
     * 获取或者创建LazySingleton
     * @return
     */
    public static LazySingleton getInstance() {
        if (instance == null) {
            // 加锁
            synchronized (LazySingleton.class) {
                // 二次判断
                if (instance == null) {
                    instance = new LazySingleton();
                }
            }
        }
        return instance;
    }



}
