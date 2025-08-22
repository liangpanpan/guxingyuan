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
public class QianxinProcessTask {
    public MultiSourceBo process() {
        try {
            long sleepTime = new Random().nextInt(10) * 1000;

            String name = "qianxin";

            System.out.println(name + " start sleepTime:" + sleepTime);
            Thread.sleep(sleepTime);
            System.out.println(name + " finish sleepTime:" + sleepTime);

            return MultiSourceBo.builder()
                    .id("002")
                    .name(name)
                    .source(name)
                    .target("222")
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
