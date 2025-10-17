package com.panpan.rocketmq.consumer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/10/16       create this file
 * </pre>
 */
@SpringBootTest
public class ConsumerServiceTest {

    @Autowired
    private ConsumerService consumerService;

    @Test
    public void testConsumer() {
        try {
            Thread.sleep(1000 * 60 * 60);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
