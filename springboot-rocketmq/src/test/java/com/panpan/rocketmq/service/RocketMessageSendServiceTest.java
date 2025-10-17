package com.panpan.rocketmq.service;

import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/10/16       create this file
 * </pre>
 */
@SpringBootTest
public class RocketMessageSendServiceTest {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Test
    public void testConvertAndSend() {
        String message = "hello";
        try {
            rocketMQTemplate.convertAndSend("sic_topic:111", message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("1111");
    }

    @Test
    public void testSendMessage() {
        String message = "hello";
        try {
            Message rocketMQMessage = new Message();
            rocketMQMessage.setBody(message.getBytes());
            rocketMQMessage.setTopic("sic_topic");
            rocketMQMessage.setKeys(UUID.randomUUID().toString());

            rocketMQTemplate.getProducer().send(rocketMQMessage);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
