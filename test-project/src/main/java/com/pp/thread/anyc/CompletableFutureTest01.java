package com.pp.thread.anyc;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/7/10       create this file
 * </pre>
 */
public class CompletableFutureTest01 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

//        test01();
//
//        test02();
//
//        test03();

        test04();
    }


    private static void test01() {
        System.out.println("start test01");

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            if (Math.random() > 0.5) {
                throw new RuntimeException("模拟异常");
            }
            return "正常结果aaa";
        });

        // 处理正常结果
        future.thenApply(result -> {
            System.out.println("处理结果: " + result);
            return result.toUpperCase();
        }).thenAccept(finalResult -> {
            System.out.println("最终结果: " + finalResult);
        });

        // 处理异常
        future.exceptionally(e -> {
            System.out.println("发生异常: " + e.getMessage());
            return "异常处理结果";
        });

        System.out.println("finish test01");
    }

    private static void test02() throws ExecutionException, InterruptedException {
        System.out.println("start test02");
        // 两个任务都完成后合并结果
        CompletableFuture<String> task1 = CompletableFuture.supplyAsync(() -> "任务1结果");
        CompletableFuture<String> task2 = CompletableFuture.supplyAsync(() -> "任务2结果");

        CompletableFuture<String> combined = task1.thenCombine(task2, (result1, result2) -> {
            return result1 + " + " + result2;
        });

        System.out.println(combined.get());

        // 所有任务完成后执行
        CompletableFuture<Void> allTasks = CompletableFuture.allOf(task1, task2);
        allTasks.thenRun(() -> {
            System.out.println("所有任务已完成");
        });

        // 任一任务完成后执行
        CompletableFuture<Object> anyTask = CompletableFuture.anyOf(task1, task2);
        anyTask.thenAccept(result -> {
            System.out.println("有任务完成: " + result);
        });

        System.out.println("finish test02");
    }


    private static void test03() {
        CompletableFuture<Void> aaa = CompletableFuture
                .supplyAsync(() -> 10)
                .thenApply(num -> num * 2)        // 20
                .thenApply(num -> num + 5)        // 25
                .thenApply(num -> {
                    if (num > 20) {
                        throw new RuntimeException("数值过大");
                    }
                    return num;
                })
                .exceptionally(e -> {
                    System.out.println("处理异常: " + e.getMessage());
                    return 0;
                })
                .thenAccept(result -> {
                    System.out.println("最终结果: " + result);
                });
    }



    public static void test04() throws ExecutionException, InterruptedException {
          // 串行操作
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "Hello");
        CompletableFuture<String> future2 = future1.thenApplyAsync(s -> s + ", World");
        CompletableFuture<String> future3 = future2.thenApplyAsync(String::toUpperCase);
        String result1 = future3.get();
        System.out.println("串行操作结果：" + result1);

        // 并行操作
        CompletableFuture<String> future4 = CompletableFuture.supplyAsync(() -> "Hello");
        CompletableFuture<String> future5 = CompletableFuture.supplyAsync(() -> "World");
        CompletableFuture<String> future6 = future4.thenCombineAsync(future5, (s1, s2) -> s1 + ", " + s2);
        CompletableFuture<String> future7 = future6.thenApplyAsync(String::toUpperCase);
        String result2 = future7.get();
        System.out.println("并行操作结果：" + result2);

        // 组合操作
        CompletableFuture<Integer> future8 = CompletableFuture.supplyAsync(() -> 5);
        CompletableFuture<Integer> future9 = CompletableFuture.supplyAsync(() -> 10);
        CompletableFuture<Integer> future10 = future8.thenCombineAsync(future9, (num1, num2) -> num1 + num2);
        CompletableFuture<String> future11 = future10.thenApplyAsync(result -> "Result: " + result);
        String result3 = future11.get();
        System.out.println("组合操作结果：" + result3);
    }

}
