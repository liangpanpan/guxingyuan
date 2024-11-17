package com.panpan.redis.test;

import com.panpan.redis.RedisApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisClusterNode;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2021/10/8       create this file
 * </pre>
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisApplication.class)
public class RedisHandlerTest {

    String key = "flow:alarmcounts:tableName:attack_ip";

    @Resource
    private RedisTemplate redisTemplate;

    @Test
    public void testIncrementScore() {


        String attackIp = "192.168.10.12";

        long startTime = System.currentTimeMillis();
        Double aDouble = redisTemplate.opsForZSet().incrementScore(key, attackIp, 1);
        long endTime = System.currentTimeMillis();

        log.info("cost time {}", (endTime - startTime));

        log.info("incrementScore {} result {}", attackIp, aDouble);
        Assert.assertTrue(aDouble > 0);

    }

    @Test
    public void testIncrementScore1() {

        ZSetOperations zSetOperations = redisTemplate.opsForZSet();

        long startTime = System.currentTimeMillis();

        Set<ZSetOperations.TypedTuple> set = zSetOperations.reverseRangeByScoreWithScores(key, 0,
                Double.MAX_VALUE, 0, 5);

        long endTime = System.currentTimeMillis();

        log.info("cost time {}", (endTime - startTime));

        set.stream().forEach(s -> log.info("value:{}, score:{}", s.getValue(), s.getScore()));

    }


    /**
     * 该测试方法，如果使用lettuce作为连接池，结束时，
     * 由于生命周期管理的问题，
     * 关闭连接池会出现如下异常
     * org.springframework.data.redis.connection.PoolException: Returned connection io.lettuce.core.cluster.StatefulRedisClusterConnectionImpl@6257af15 was either previously returned or does not belong to this connection provider
     * 使用jedis连接池测试无问题
     */
    @Test
    public void testScan01() {
        String matchKey = "uuid:key:*";

        Set<String> keys = new HashSet();

        RedisClusterConnection clusterConnection = redisTemplate.getConnectionFactory().getClusterConnection();
        Iterable<RedisClusterNode> redisClusterNodes = clusterConnection.clusterGetNodes();
        Iterator<RedisClusterNode> iterator = redisClusterNodes.iterator();
        while (iterator.hasNext()) {
            RedisClusterNode next = iterator.next();
            Cursor<byte[]> scan = clusterConnection.scan(next, ScanOptions.scanOptions().match(matchKey).count(1000).build());
            while (scan.hasNext()) {
                keys.add(new String(scan.next()));
            }
        }

        keys.stream().forEach(System.out::println);

        System.out.println(keys.size());

    }


    /**
     * 建议采用该方法
     */
    @Test
    public void testScan02() {
        String matchKey = "uuid:key:*";
        Set<String> results = new HashSet();

        RedisSerializer serializer = redisTemplate.getKeySerializer();
        ScanOptions scanOptions = ScanOptions.scanOptions().match(matchKey).count(100).build();
        Cursor<byte[]> cursor = (Cursor<byte[]>) redisTemplate
                .execute((RedisCallback<Cursor<byte[]>>) connection -> connection.scan(scanOptions), true);
        while (cursor.hasNext()) {
            results.add(String.valueOf(serializer.deserialize(cursor.next())));
        }

        results.stream().forEach(System.out::println);
        System.out.println(results.size());
    }


    @Test
    public void testScan03() {
        String matchKey = "uuid:key:*";

        Set<String> results = new HashSet();

        redisTemplate.execute((RedisCallback<Set<String>>) connection -> {

            try (Cursor<byte[]> cursor = connection.scan(new ScanOptions.ScanOptionsBuilder()
                    .match(matchKey)
                    .count(10000).build())) {

                while (cursor.hasNext()) {
                    results.add(new String(cursor.next(), "Utf-8"));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return results;
        });


        results.stream().forEach(System.out::println);
        System.out.println(results.size());
    }

}
