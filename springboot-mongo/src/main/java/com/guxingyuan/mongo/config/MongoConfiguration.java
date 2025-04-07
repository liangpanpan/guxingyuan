package com.guxingyuan.mongo.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.MongoDriverInformation;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.internal.MongoClientImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/4/2       create this file
 * </pre>
 */
@Slf4j
@Configuration
public class MongoConfiguration {

    /**
     * 定义mongo配置
     *
     * @return mongo配置
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.mongodb")
    @ConditionalOnMissingBean
    public MongoProperties getMongoProperties() {
        return new MongoProperties();
    }


    /**
     * 转换器
     * MappingMongoConverter 可以自定义mongo转换器，住院自定义存取mongo数据时的一些操作，
     * 例如 mappingConverter.setTypeMapper(new DefaultMongoTypeMapper(null)); 方法会将Mongo数据中的_class 字段去掉
     *
     * @param factory     mongo工厂
     * @param context     上下文
     * @param beanFactory 自定义转换器
     * @return 转换器对象
     */
    @Bean
    public MappingMongoConverter mappingMongoConverter(MongoDatabaseFactory factory, MongoMappingContext context, BeanFactory beanFactory) {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
        MappingMongoConverter mappingConverter = new MappingMongoConverter(dbRefResolver, context);
        try {
            mappingConverter.setCustomConversions(beanFactory.getBean(CustomConversions.class));
        } catch (NoSuchBeanDefinitionException ignore) {

        }

        // 保存不需要 -class to 字段 mongo
        mappingConverter.setTypeMapper(new DefaultMongoTypeMapper(null));

        return mappingConverter;
    }

    @Bean
    public MongoTypeMapper defaultMongoTypeMapper() {
        return new DefaultMongoTypeMapper(null);
    }

    @Bean
    public MongoClientSettings mongoClientSettings(MongoProperties properties) {
        // 创建认证
        MongoCredential mongoCredential = getMongoCredential(properties);

        // 解析获取Mongo服务器地址信息
        List<ServerAddress> serverAddresses = getServerAddresses(properties.getAddress());

        /**
         * 还可以使用URI进行配置
         * .applyConnectionString(new ConnectionString("mongodb://localhost:27017"))
         * .applyConnectionString(new ConnectionString("mongodb://username:password@host1:27017,host2:27017/database?authSource=admin"))
         */

        MongoClientSettings.Builder settingBuilder = MongoClientSettings.builder()
                // 客户端标识，用于定位请求来源等，一般是程序名
                .applicationName(properties.getClientName())
                // 配置IP地址
                .applyToClusterSettings(builder -> builder.hosts(serverAddresses))
                // 配置认证
//                .credential(mongoCredential)

                .applyToSocketSettings(i -> i
                        // TCP (socket) 连接超时时间，毫秒
                        .connectTimeout(properties.getConnectionTimeoutMs(), TimeUnit.MILLISECONDS)
                        // TCP (socket) 读取超时时间，毫秒
                        .readTimeout(properties.getReadTimeoutMs(), TimeUnit.MILLISECONDS))
                .applyToConnectionPoolSettings(i -> i
                        // TCP (socket) 连接闲置时间
                        .maxConnectionIdleTime(properties.getMaxConnectionIdleTime(), TimeUnit.MILLISECONDS)
                        // TCP (socket) 连接最多可以使用多久， 毫秒
                        .maxConnectionLifeTime(properties.getMaxConnectionLifeTime(), TimeUnit.MILLISECONDS)
                        // 当连接池无可用连接时，客户端阻塞等待的最大时长
                        .maxWaitTime(properties.getMaxWaitTime(), TimeUnit.MILLISECONDS)
                        // 最小线程数
                        .minSize(properties.getMinSize())
                        // 最大线程数
                        .maxSize(properties.getMaxSize()))
                .applyToServerSettings(i -> i
                        // 心跳检测发送频率，毫秒
                        .heartbeatFrequency(properties.getHeartbeatFrequency(), TimeUnit.MILLISECONDS)
                        // 最小的心跳检测发送频率，毫秒
                        .minHeartbeatFrequency(properties.getMinHeartbeatFrequency(), TimeUnit.MILLISECONDS));

        // 配置认证
        if (mongoCredential != null) {
            settingBuilder.credential(mongoCredential);
        }

        return settingBuilder.build();
    }

    /**
     * 自定义Mongo连接池
     *
     * @param mongoProperties
     * @return
     */
    @Bean
    @Primary
    public MongoDatabaseFactory mongoDbFactory(MongoProperties mongoProperties) {
        // 创建客户端参数
        MongoClientSettings mongoClientSettings = mongoClientSettings(mongoProperties);
        // mongo client
        MongoDriverInformation info = MongoDriverInformation.builder().build();

        MongoClient mongoClient = new MongoClientImpl(mongoClientSettings, info);

        return new SimpleMongoClientDatabaseFactory(mongoClient, mongoProperties.getDatabase());
    }


    /**
     * 还可以创建一个MongoDatabase
     *
     * @param mongoProperties
     * @return
     */
    @Bean
    public MongoDatabase getMongoDatabase(MongoProperties mongoProperties) {
        MongoClient mongoClient = MongoClients.create(mongoClientSettings(mongoProperties));
        return mongoClient.getDatabase(mongoProperties.getDatabase());
    }


    @Bean
    public MongoTemplate mongoTemplate(MongoConverter mongoConverter) {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory(getMongoProperties()), mongoConverter);
        return mongoTemplate;
    }


    /**
     * 获取数据库服务地址
     *
     * @param mongoAddress
     * @return
     */
    private List<ServerAddress> getServerAddresses(String mongoAddress) {
        String[] mongoAddressArray = mongoAddress.trim().split(",");
        List<ServerAddress> serverAddresses = new ArrayList<>();
        for (String address : mongoAddressArray) {
            String[] hostAndPort = address.split(":");
            serverAddresses.add(new ServerAddress(hostAndPort[0], Integer.parseInt(hostAndPort[1])));
        }
        return serverAddresses;
    }


    /**
     * 创建认证信息
     *
     * @param mongoProperties
     * @return
     */
    private MongoCredential getMongoCredential(MongoProperties mongoProperties) {
        if (StringUtils.isNoneBlank(mongoProperties.getUsername()) && StringUtils.isNotBlank(mongoProperties.getPassword())) {
            // 都不为空
            String database = StringUtils.isEmpty(mongoProperties.getDatabase()) ? "admin" : mongoProperties.getDatabase();

            return MongoCredential.createCredential(mongoProperties.getUsername(), database, mongoProperties.getPassword().toCharArray());
        }
        return null;
    }


}
