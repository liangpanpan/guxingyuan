package com.panpan.redis.test;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.StringUtils;
import redis.clients.jedis.*;

import java.util.*;

/**
 * 测试Redis命令
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2024/10/22       create this file
 * </pre>
 */
public class RedisCommandTest {

    private JedisPool sessionCachePool;

    private JedisCluster jedisCluster;

    private int singleOrCluster = 2;

    @Before
    public void before() {
        // 开始之前先创建Redis 客户端
        String redis_host = "10.1.1.50:8001,10.1.1.50:8002,10.1.1.50:8003,10.1.1.50:8004,10.1.1.50:8005,10.1.1.50:8006";

        int redis_port = 8001;
        String redis_passwd = "TJym@1230123!";
        int timeout = 0;

        GenericObjectPoolConfig pool_config = new GenericObjectPoolConfig();
        pool_config.setTestOnBorrow(true);
        pool_config.setMinIdle(1);
        pool_config.setMaxIdle(10);
        pool_config.setMaxTotal(100);
        pool_config.setBlockWhenExhausted(false);
        pool_config.setMaxWaitMillis(2000);

        if (singleOrCluster == 1) {
            if (StringUtils.hasLength(redis_passwd)) {
                sessionCachePool = new JedisPool(pool_config, redis_host, redis_port, timeout, redis_passwd);
            } else {
                sessionCachePool = new JedisPool(pool_config, redis_host, redis_port);
            }
        } else {
            String[] redis_hosts = redis_host.split(",");
            Set<HostAndPort> nodeSet = new HashSet<>();
            for (int i = 0; i < redis_hosts.length; i++) {
                String[] hostAndPort = redis_hosts[i].split(":");
                if (hostAndPort.length == 2) {
                    HostAndPort node = new HostAndPort(hostAndPort[0], Integer.parseInt(hostAndPort[1]));
                    nodeSet.add(node);
                }
            }

            if (StringUtils.hasLength(redis_passwd)) {
                jedisCluster = new JedisCluster(nodeSet, 5000, 5000, 5, redis_passwd, pool_config);
            } else {
                jedisCluster = new JedisCluster(nodeSet, pool_config);
            }

        }


        // 集群 配置


        System.out.println("start create redis single connection++++++++++++++++");
    }

    @Test
    public void testGetValue() {
        Jedis redisClient = sessionCachePool.getResource();
        try {
            String test = redisClient.set("test", "123");
            System.out.println("set:" + test);
            System.out.println("value:" + redisClient.get("test"));
        } finally {
            redisClient.close();
        }
    }


    @Test
    public void testBatchSetValue() {
        Jedis redisClient = sessionCachePool.getResource();
        try {
            for (int i = 0; i < 10; i++) {
                redisClient.set("batch_key:" + i, i + "");
            }
        } finally {
            redisClient.close();
        }
        System.out.println("finish");
    }

    @Test
    public void testKeys() {
        Jedis jedis1 = sessionCachePool.getResource();
        Jedis jedis2 = sessionCachePool.getResource();

        jedis1.set("key1", "good job");
        jedis1.set("key2", "holy crap");

        System.out.println(jedis1.get("key1") + ", " + jedis2.get("key2"));

//        sessionCachePool.returnResource(jedis1);
        jedis1.close();

        System.out.println("redis池回收jedis1之后:");
        System.out.println(jedis1.get("key1") + ", " + jedis2.get("key2"));
        System.out.println("redis池回收jedis1之后2:");
        System.out.println(jedis1.get("key1") + ", " + jedis2.get("key2"));
    }


    @Test
    public void testClose() {
        Jedis redisClient = sessionCachePool.getResource();
        try {

        } finally {
            redisClient.close();
        }
    }


    @Test
    public void testScan() {
        Jedis jedis = sessionCachePool.getResource();
        try {

            String pattern = "batch_key:*";
            int limit = 3;

            ScanParams scanParams = new ScanParams();
            scanParams.match(pattern);
            scanParams.count(limit);

            String cursor = "";

            List<String> scanList = new LinkedList<>();

            while (true) {
                long begin = System.currentTimeMillis();
                ScanResult<String> scanResult = jedis.scan(cursor, scanParams);

                System.out.println("耗时：" + (System.currentTimeMillis() - begin) + "ms,查询数目：" + scanResult.getResult().size());
                scanList.addAll(scanResult.getResult());
                cursor = scanResult.getStringCursor();
                System.out.println("cursor:" + cursor);

                if (ScanParams.SCAN_POINTER_START.equals(scanResult.getStringCursor())) {
                    break;
                }
            }

            scanList.stream().forEach(System.out::println);
        } finally {
            jedis.close();
        }
    }


    @Test
    public void testClusterPut() {
        String key = "uuid:key:";
        for (int i = 0; i < 100; i++) {
            String uuid = UUID.randomUUID().toString();
            String nowKey = key + uuid;
            String s = jedisCluster.set(nowKey, uuid);
            System.out.println(s);
            System.out.println("get result:" + jedisCluster.get(nowKey));
        }
    }


    @Test
    public void testClusterScan() {
        String cursor = "";
        String pattern = "uuid:key:*";

        ScanParams scanParams = new ScanParams();
        scanParams.match(pattern);
        scanParams.count(100);

        Set<String> result = new TreeSet<>();
        Map<String, JedisPool> clusterNodes = jedisCluster.getClusterNodes();
        for (String k : clusterNodes.keySet()) {
            JedisPool jp = clusterNodes.get(k);
            Jedis sc = jp.getResource();
            try {
                while (true) {
                    ScanResult<String> scanResult = sc.scan(cursor, scanParams);
                    result.addAll(scanResult.getResult());
                    cursor = scanResult.getStringCursor();

                    System.out.println("cursor:" + cursor + " size:" + scanResult.getResult().size());

                    if (ScanParams.SCAN_POINTER_START.equals(scanResult.getStringCursor())) {
                        break;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                sc.close();//用完一定要close这个链接！！！
            }
        }

        for (String key : result) {
            System.out.println("key is:" + key + " value:" + jedisCluster.get(key));
        }

        System.out.println("result size:" + result.size());
    }

}
