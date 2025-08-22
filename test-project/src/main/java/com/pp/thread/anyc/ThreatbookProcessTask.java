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
public class ThreatbookProcessTask {
    public MultiSourceBo process() {
        try {
            long sleepTime = new Random().nextInt(10) * 1000;

            String name = "threadBook";

            System.out.println(name + " start sleepTime:" + sleepTime);
            Thread.sleep(sleepTime);
            System.out.println(name + " finish sleepTime:" + sleepTime);

            return MultiSourceBo.builder()
                    .id("003")
                    .name(name)
                    .source(name)
                    .target("333")
                    .build();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
