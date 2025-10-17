package com.panpan;

import org.apache.rocketmq.client.apis.ClientConfiguration;
import org.apache.rocketmq.client.apis.ClientException;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.apache.rocketmq.client.apis.StaticSessionCredentialsProvider;
import org.apache.rocketmq.client.apis.consumer.FilterExpression;
import org.apache.rocketmq.client.apis.consumer.FilterExpressionType;
import org.apache.rocketmq.client.apis.consumer.SimpleConsumer;
import org.apache.rocketmq.client.apis.message.MessageView;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Collections;
import java.util.List;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/10/10       create this file
 * </pre>
 */
public class CustomerExample01 {

    private static final String ENDPOINTS = "10.1.1.231:8081";
    private static final String TOPIC = "sic_topic";
    private static final String CONSUMER_GROUP = "White-ConsumerGroup";

    public static void main(String[] args) throws ClientException {
        // 创建消费者实例
        ClientServiceProvider provider = ClientServiceProvider.loadService();
        ClientConfiguration clientConfiguration = ClientConfiguration.newBuilder()
                .setEndpoints(ENDPOINTS)
                .build();

        String tag = "whitelist";

        FilterExpression filterExpression = new FilterExpression(tag, FilterExpressionType.TAG);

        SimpleConsumer consumer = provider.newSimpleConsumerBuilder()
                .setClientConfiguration(clientConfiguration)
                .setConsumerGroup(CONSUMER_GROUP)
                .setAwaitDuration(Duration.ofSeconds(10))
                .setSubscriptionExpressions(Collections.singletonMap(TOPIC, filterExpression))
                .build();

        // 持续接收消息
        while (true) {
            try {
                // 接收消息
                List<MessageView> messageViews = consumer.receive(10, Duration.ofSeconds(10));

                for (MessageView messageView : messageViews) {
                    // 处理消息
                    processMessage(messageView);

                    // 确认消息
                    consumer.ack(messageView);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private static void processMessage(MessageView messageView) {
        try {
            // 获取消息内容
            // 获取消息内容
            ByteBuffer byteBuffer = messageView.getBody();
            byte[] dst = new byte[byteBuffer.remaining()];
            byteBuffer.get(dst); // 从当前位置读取到数组

            String messageBody = new String(dst, StandardCharsets.UTF_8);

            // 获取消息属性
            String messageId = messageView.getMessageId().toString();
            String topic = messageView.getTopic();
            String tag = messageView.getTag().orElse("");
            long bornTimestamp = messageView.getBornTimestamp();
            long deliveryAttempt = messageView.getDeliveryAttempt();

            // 获取自定义属性
            String customProp = messageView.getProperties().getOrDefault("customKey", "");

            System.out.println("=== 收到消息 ===");
            System.out.println("消息ID: " + messageId);
            System.out.println("主题: " + topic);
            System.out.println("标签: " + tag);
            System.out.println("消息体: " + messageBody);
            System.out.println("生产时间: " + new java.util.Date(bornTimestamp));
            System.out.println("投递次数: " + deliveryAttempt);
            System.out.println("自定义属性: " + customProp);
            System.out.println("===============");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
