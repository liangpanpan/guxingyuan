package com.panpan.rocketmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/10/16       create this file
 * </pre>
 */
@Component
@Slf4j
@RocketMQMessageListener(
        topic = "sic_topic",
        selectorExpression = "111",  // 只消费 tag1 的消息
        consumerGroup = "demo-consumer-group"
)
public class ConsumerService implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info("receive message:{}", message);
    }
}


