package com.pp.thread.anyc;

import java.util.List;
import java.util.concurrent.*;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2026/1/15       create this file
 * </pre>
 */
public class FutureNotAsyncTest {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(8);


        CompletableFuture<Void> voidCompletableFuture = CompletableFuture
                .supplyAsync(() -> {
//                    try {
//                        TimeUnit.SECONDS.sleep(1);
//                    } catch (Exception ex) {
//
//                    }
                    System.out.println("supplyAsync:" + Thread.currentThread());
                    return "abc_";
                }, executor)
                .thenApply(param -> {
                    System.out.println(Thread.currentThread());
                    return param.toUpperCase();
                })
                .thenCompose(f -> {
                    System.out.println(Thread.currentThread());
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (Exception e) {
                    }
                    return CompletableFuture.supplyAsync(() -> f + "456_", executor);
                })
                .thenCompose(f -> {
                    System.out.println(Thread.currentThread());
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (Exception e) {
                    }
                    return CompletableFuture.supplyAsync(() -> f + "789", executor);
                })
                .thenAccept(System.out::println);
        voidCompletableFuture.join();
        executor.shutdown();

        boolean terminated = executor.awaitTermination(1, TimeUnit.SECONDS);
        if (!terminated) {
            List<Runnable> remainTaskList = executor.shutdownNow();
            if (!remainTaskList.isEmpty()) {
                for (Runnable runnable : remainTaskList) {
                    runnable.run();
                }
            }
        }
    }
}
