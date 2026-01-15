package com.pp.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2026/1/15       create this file
 * </pre>
 */
public class ThreadPoolTest {

    public static void main(String[] args) {
        // 1. 配置线程池参数
        int corePoolSize = 2; // 核心线程数
        int maximumPoolSize = 5; // 最大线程数
        long keepAliveTime = 60L; // 非核心线程空闲存活时间
        TimeUnit unit = TimeUnit.SECONDS; // 存活时间单位
        int queueCapacity = Integer.MAX_VALUE; // 队列容量（Integer.MAX_VALUE=无界队列，实现队列大小可空）

        // 2. 先声明线程池（后续注入到自定义队列）
        ThreadPoolExecutor threadPoolExecutor = null;

        // 3. 实例化自定义队列（注入线程池引用）
        PriorityThreadBlockingQueue<Runnable> customQueue = new PriorityThreadBlockingQueue<>(
                15,
                threadPoolExecutor
        );

        // 4. 实例化线程池（传入自定义队列）
        threadPoolExecutor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                customQueue, // 自定义队列
                Executors.defaultThreadFactory(), // 默认线程工厂
                new ThreadPoolExecutor.DiscardPolicy() // 默认拒绝策略
        );

        // 5. 重新给队列设置线程池引用（解决先声明后实例化的引用问题）
        customQueue.setThreadPoolExecutor(threadPoolExecutor);

        // 6. 测试：提交10个任务，验证执行逻辑
        for (int i = 0; i < 30; i++) {
            final int taskNum = i;
            threadPoolExecutor.submit(() -> {
                try {
                    System.out.println("任务" + taskNum + "正在执行，执行线程：" + Thread.currentThread().getName());
                    TimeUnit.SECONDS.sleep(5); // 模拟任务执行耗时
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            System.out.println("任务" + taskNum + "已提交，当前线程池运行线程数：" + threadPoolExecutor.getPoolSize());
        }

        // 7. 关闭线程池
        threadPoolExecutor.shutdown();
        try {
            if (!threadPoolExecutor.awaitTermination(1, TimeUnit.MINUTES)) {
                threadPoolExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            threadPoolExecutor.shutdownNow();
        }
    }
}

// 补充：给自定义队列添加set方法，解决线程池引用注入问题
class PriorityThreadBlockingQueue<E> extends LinkedBlockingQueue<E> {
    private static final long serialVersionUID = 7429220726917448502L;

    private ThreadPoolExecutor threadPoolExecutor;

    public PriorityThreadBlockingQueue(int capacity, ThreadPoolExecutor threadPoolExecutor) {
        super(capacity);
        this.threadPoolExecutor = threadPoolExecutor;
    }

    // 新增set方法，用于后续更新线程池引用
    public void setThreadPoolExecutor(ThreadPoolExecutor threadPoolExecutor) {
        this.threadPoolExecutor = threadPoolExecutor;
    }

    @Override
    public boolean offer(E e) {
        if (threadPoolExecutor == null) {
            return super.offer(e);
        }
        int currentPoolSize = threadPoolExecutor.getPoolSize();
        int maxPoolSize = threadPoolExecutor.getMaximumPoolSize();

        if (currentPoolSize < maxPoolSize) {
            return false;
        }
        return super.offer(e);
    }

    @Override
    public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
        if (threadPoolExecutor == null) {
            return super.offer(e, timeout, unit);
        }
        int currentPoolSize = threadPoolExecutor.getPoolSize();
        int maxPoolSize = threadPoolExecutor.getMaximumPoolSize();

        if (currentPoolSize < maxPoolSize) {
            return false;
        }
        return super.offer(e, timeout, unit);
    }
}
