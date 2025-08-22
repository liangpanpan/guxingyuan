package com.pp.thread.anyc;

import com.alibaba.fastjson2.JSONObject;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/7/10       create this file
 * </pre>
 */
public class AnycThread {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // 使用默认的ForkJoinPool执行异步任务
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            // 模拟耗时操作
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "任务结果";
        });

        // 阻塞获取结果
//        String result = future.get();
//        System.out.println(result);

        System.out.println("1111");
        // 或者使用回调

        future.thenAccept(result -> System.out.println("回调获取结果: " + result));

        future.get();
        System.out.println("2222");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        System.out.println("start:" + sdf.format(new Date()));

        CompletableFuture<MultiSourceBo> tj = CompletableFuture.supplyAsync(() -> new TJProcessTask().process());
        CompletableFuture<MultiSourceBo> qianxin = CompletableFuture.supplyAsync(() -> new QianxinProcessTask().process());
        CompletableFuture<MultiSourceBo> threatbook = CompletableFuture.supplyAsync(() -> new ThreatbookProcessTask().process());
        CompletableFuture<MultiSourceBo> localData = CompletableFuture.supplyAsync(() -> new LocalDataProcessTask().process());

        System.out.println("111:" + sdf.format(new Date()));

        List<CompletableFuture<MultiSourceBo> > futures = Arrays.asList(tj, qianxin, threatbook, localData);


        CompletableFuture<Void> allDone = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

        System.out.println("222:" + sdf.format(new Date()));

        List<MultiSourceBo> list = allDone.thenApply(v ->
                        futures.stream()
                                .map(CompletableFuture::join)
                                .collect(Collectors.toList()))
                .join();

        System.out.println("333:" + sdf.format(new Date()));


        list.forEach(v -> {
                    System.out.println(JSONObject.toJSONString(v));
                }
        );

        System.out.println("finish:" + sdf.format(new Date()));
    }


}
