package com.guxingyuan.mongo.config;

import lombok.Data;
import lombok.ToString;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/4/2       create this file
 * </pre>
 */
@Data
@ToString
public class MongoProperties {

    /**
     * 客户端标识名，
     */
    private String clientName;

    /**
     * 数据库
     */
    private String database;

    /**
     * 多个配合，中间用逗号分隔，IP和端口号中间用引号:分隔
     */
    private String address;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 最大线程数
     */
    private Integer maxSize;

    /**
     * 最小线程数
     */
    private Integer minSize;

    /**
     * TCP (socket) 连接最多可以使用多久， 毫秒
     */
    private Integer maxConnectionLifeTime;

    /**
     * 最大活跃时间
     */
    private Integer maxConnectionIdleTime;

    /**
     * 最大等待时长
     */
    private Integer maxWaitTime;

    /**
     * 读取结果超时时间
     */
    private Integer readTimeoutMs;

    /**
     * 连接超时时间
     */
    private Integer connectionTimeoutMs;

    /**
     * 心跳检测发送频率
     */
    private Integer heartbeatFrequency;

    /**
     * 最小的心跳检测发送频率
     */
    private Integer minHeartbeatFrequency;


}
