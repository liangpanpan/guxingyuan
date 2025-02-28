package com.panpan.logback.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2021/8/7       create this file
 * </pre>
 */
//@Component
public class SimpleScheduleTask {

    public static final Logger logger = LoggerFactory.getLogger(SimpleScheduleTask.class);

    @Scheduled(cron = "0/5 * * * * ?")
    public void tast01() {
        logger.info("test01");
    }


    @Scheduled(cron = "0/10 * * * * ?")
    public void tast02() {
        logger.error("test02");
    }


    @Scheduled(cron = "0/30 * * * * ?")
    public void tast03() {
        logger.error("test03", new RuntimeException("error test 03"));
    }

}
