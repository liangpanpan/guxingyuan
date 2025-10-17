package com.panpan.rocketmq.producer;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.impl.producer.DefaultMQProducerImpl;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.logging.org.slf4j.Logger;
import org.apache.rocketmq.logging.org.slf4j.LoggerFactory;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/10/16       create this file
 * </pre>
 */
public class DirectProducer {

    private final static Logger log = LoggerFactory.getLogger(DefaultMQProducerImpl.class);

    public static void main(String[] args) throws MQClientException {
        log.info("111111");
        // 1. 创建生产者，指定生产者组
        DefaultMQProducer producer = new DefaultMQProducer("direct-producer-group");

        // 2. 设置 NameServer 地址（关键：直连模式必须配置）
        producer.setNamesrvAddr("10.1.1.231:9876");  // 替换为实际 NameServer 地址

        producer.setSendMsgTimeout(3000);
        producer.setDetectTimeout(3000);

        // 3. 启动生产者
        producer.start();

        try {
            // 4. 创建消息（主题：demo-topic，标签：tag1，内容：字节数组）
            Message message = new Message(
                    "sic_topic",  // 主题
                    "tag1",        // 标签
                    "Hello RocketMQ 5.3.x".getBytes()  // 消息体
            );

            // 5. 发送消息（同步发送）
            producer.send(message);
            System.out.println("消息发送成功");
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            // 6. 关闭生产者
            producer.shutdown();
        }
    }

}
