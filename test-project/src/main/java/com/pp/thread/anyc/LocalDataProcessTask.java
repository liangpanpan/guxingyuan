package com.pp.thread.anyc;

import java.util.Random;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/7/10       create this file
 * </pre>
 */
public class LocalDataProcessTask {
    public MultiSourceBo process() {
        try {
            long sleepTime = new Random().nextInt(10) * 1000;

            String name = "local data";

            System.out.println(name + " start sleepTime:" + sleepTime);
            Thread.sleep(sleepTime);
            System.out.println(name + " finish sleepTime:" + sleepTime);

            return MultiSourceBo.builder().id("004").name(name).source(name).target("444").build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
