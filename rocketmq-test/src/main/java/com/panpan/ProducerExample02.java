package com.panpan;

import org.apache.rocketmq.client.apis.ClientConfiguration;
import org.apache.rocketmq.client.apis.ClientException;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.apache.rocketmq.client.apis.StaticSessionCredentialsProvider;
import org.apache.rocketmq.client.apis.message.Message;
import org.apache.rocketmq.client.apis.producer.Producer;
import org.apache.rocketmq.client.apis.producer.SendReceipt;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;

/**
 * 不开启ACL 账户登录
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/10/10       create this file
 * </pre>
 */
public class ProducerExample02 {

    public static void main(String[] args) {
        try {

            /**
             * 实例接入点，从控制台实例详情页的接入点页签中获取。
             * 如果是在阿里云ECS内网访问，建议填写VPC接入点。
             * 如果是在本地公网访问，或者是线下IDC环境访问，可以使用公网接入点。使用公网接入点访问，必须开启实例的公网访问功能。
             *
             * 开启RocketMQ的Proxy后，必须请求Proxy的端口：8080或8081，访问nameser的9876会失败
             *
             */
            String endpoints = "10.1.1.231:9876";
            //消息发送的目标Topic名称，需要提前在控制台创建，如果不创建直接使用会返回报错。
            String topic = "sic_topic";


            ClientServiceProvider provider = ClientServiceProvider.loadService();
            ClientConfiguration clientConfiguration = ClientConfiguration.newBuilder()
                    .setEndpoints(endpoints)
                    /**
                     * 如果使用公网接入点访问Serverless实例，需要设置实例ID。
                     */
                    //.setNamespace("InstanceId")
                    /**
                     * 如果是使用公网接入点访问，configuration还需要设置实例的用户名和密码。用户名和密码在控制台访问控制的智能身份识别页签中获取。
                     * 如果是在阿里云ECS内网访问，无需填写该配置，服务端会根据内网VPC信息智能获取。
                     * 如果实例类型为Serverless实例，公网访问必须设置实例的用户名密码，当开启内网免身份识别时,内网访问可以不设置用户名和密码。
                     */
                    //.setCredentialProvider(new StaticSessionCredentialsProvider("Instance UserName", "Instance Password"))
                    .build();
            /**
             * 初始化Producer时直接配置需要使用的Topic列表（这个参数可以配置多个Topic），实现提前检查错误配置、拦截非法配置启动。
             * 针对非事务消息 Topic，也可以不配置，服务端会动态检查消息的Topic是否合法。
             * 注意！！！事务消息Topic必须提前配置，以免事务消息回查接口失败，具体原理请参见事务消息。
             */
            Producer producer = provider.newProducerBuilder()
                    .setTopics(topic)
                    .setClientConfiguration(clientConfiguration)
                    .build();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


            // whitelist
            // test01
            String tag = "whitelist";

            for (int i = 0; i < 10; i++) {
                String error = "";
                if (i % 2 == 0) {
                    error = "error";
                }

                String messageBody = "tag is:" + tag + " message body! " + error + "index:" + i + " time:" + sdf.format(System.currentTimeMillis());

                //普通消息发送。
                Message message = provider.newMessageBuilder()
                        .setTopic(topic)
                        //设置消息索引键，可根据关键字精确查找某条消息。
                        .setKeys("messageKey")
                        //设置消息Tag，用于消费端根据指定Tag过滤消息。
                        .setTag(tag)
                        //消息体。
                        .setBody(messageBody.getBytes())
                        .build();

                //发送消息，需要关注发送结果，并捕获失败等异常。
                SendReceipt sendReceipt = producer.send(message);
                System.out.println(sendReceipt.getMessageId());

//                Thread.sleep(Duration.ofSeconds(1).toMillis());

            }
            producer.close();
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
