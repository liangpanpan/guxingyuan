package com.panpan;

import ch.qos.logback.core.db.BindDataSourceToJNDIAction;
import org.apache.rocketmq.client.apis.ClientConfiguration;
import org.apache.rocketmq.client.apis.ClientException;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.apache.rocketmq.client.apis.StaticSessionCredentialsProvider;
import org.apache.rocketmq.client.apis.consumer.ConsumeResult;
import org.apache.rocketmq.client.apis.consumer.FilterExpression;
import org.apache.rocketmq.client.apis.consumer.FilterExpressionType;
import org.apache.rocketmq.client.apis.consumer.PushConsumer;
import org.apache.rocketmq.client.apis.message.MessageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

/**
 * 自动确认消息
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/10/10       create this file
 * </pre>
 */
public class PushConsumerExample {

    private static final Logger logger = LoggerFactory.getLogger(PushConsumerExample.class);

    private PushConsumerExample() {
    }

    public static void main(String[] args) throws ClientException, IOException, InterruptedException {
        final ClientServiceProvider provider = ClientServiceProvider.loadService();

        // 账号
        String ACCESS_KEY = "sicAdmin";
        // 密码
        String SECRET_KEY = "123456";
        StaticSessionCredentialsProvider sessionCredentialsProvider = new StaticSessionCredentialsProvider(ACCESS_KEY, SECRET_KEY);

        // 接入点地址，需要设置成Proxy的地址和端口列表，一般是xxx:8081;xxx:8081。
        String endpoints = "10.1.1.231:8081";
        ClientConfiguration clientConfiguration = ClientConfiguration.newBuilder()
                .setCredentialProvider(sessionCredentialsProvider)
                .setEndpoints(endpoints)
                .build();
        // 订阅消息的过滤规则，表示订阅所有Tag的消息。
        String tag = "test01";
        FilterExpression filterExpression = new FilterExpression(tag, FilterExpressionType.TAG);
        // 为消费者指定所属的消费者分组，Group需要提前创建。
        String consumerGroup = "YourConsumerGroup";
        // 指定需要订阅哪个目标Topic，Topic需要提前创建。
        String topic = "sic_topic";
        // 初始化PushConsumer，需要绑定消费者分组ConsumerGroup、通信参数以及订阅关系。
        PushConsumer pushConsumer = provider.newPushConsumerBuilder()
                .setClientConfiguration(clientConfiguration)
                // 设置消费者分组。
                .setConsumerGroup(consumerGroup)
                // 设置预绑定的订阅关系。
                .setSubscriptionExpressions(Collections.singletonMap(topic, filterExpression))
                // 设置消费监听器。
                .setMessageListener(messageView -> {
                    // 处理消息并返回消费结果。
                    logger.info("Consume message successfully, messageId={}", messageView.getMessageId());

                    boolean result = processMessage(messageView);
                    if (!result) {
                        return ConsumeResult.FAILURE;
                    }
                    return ConsumeResult.SUCCESS;
                })
                .build();
//        Thread.sleep(Long.MAX_VALUE);
//        // 如果不需要再使用 PushConsumer，可关闭该实例。
//        pushConsumer.close();

        // 3. 保持应用运行
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                pushConsumer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));

    }


    private static boolean processMessage(MessageView messageView) {
        try {
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

//            if (messageBody.contains("error")) {
//                System.out.println("处理失败");
//                return false;
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

}
