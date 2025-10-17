package com.panpan.rocketmq.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/10/16       create this file
 * </pre>
 */
@Slf4j
@Service
public class RocketMessageSendService {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public void sendMessage(String message) {
        try {
            rocketMQTemplate.convertAndSend("sic_topic:111", message);

//            Message rocketMQMessage = new Message();
//            rocketMQMessage.setBody(message.getBytes());
//            rocketMQMessage.setTopic("sic_topic");
//            rocketMQMessage.setKeys(UUID.randomUUID().toString());
//
//            rocketMQTemplate.getProducer().send(rocketMQMessage);

        } catch (Exception ex) {
            log.error("send message:{} error", message, ex);
        }
    }

}
