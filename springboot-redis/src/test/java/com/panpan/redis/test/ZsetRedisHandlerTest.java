package com.panpan.redis.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.panpan.redis.RedisApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2024/2/25       create this file
 * </pre>
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisApplication.class)
public class ZsetRedisHandlerTest {

    @Resource
    private RedisTemplate redisTemplate;


    @Test
    public void testListZSet() {
        Set<ZSetOperations.TypedTuple<String>> set = redisTemplate.opsForZSet().reverseRangeWithScores("zset:test:20240225", 0,
                -1);
        JSONArray jsonArray = JSONObject.parseArray(JSONObject.toJSONString(set));

        for (int i = 0, size = jsonArray.size(); i < size; i++) {
            JSONObject o = JSONObject.parseObject(jsonArray.get(i).toString());
            System.out.println("member：" + o.getString("value") + ", 请求次数：" + o.getLongValue("score"));
        }

    }


    @Test
    public void testUnionAndStore() {
        List<String> keys = new ArrayList<>();
        keys.add("zset:test:20240225");
        keys.add("zset:test:20240224");
        keys.add("zset:test:20240223");
        keys.add("zset:test:20240222");

        // 先将所有的Key移除掉
        redisTemplate.delete("zset:test:20240225");
        redisTemplate.delete("zset:test:20240224");
        redisTemplate.delete("zset:test:20240223");
        redisTemplate.delete("zset:test:20240222");

        redisTemplate.opsForZSet().incrementScore("zset:test:20240225", "张三", 10);
        redisTemplate.opsForZSet().incrementScore("zset:test:20240225", "李四", 8);
        redisTemplate.opsForZSet().incrementScore("zset:test:20240225", "王五", 5);
        redisTemplate.opsForZSet().incrementScore("zset:test:20240225", "赵六", 4);
        redisTemplate.opsForZSet().incrementScore("zset:test:20240225", "第七", 3);
        redisTemplate.opsForZSet().incrementScore("zset:test:20240225", "第八", 2);

        redisTemplate.opsForZSet().incrementScore("zset:test:20240224", "张三", 3);
        redisTemplate.opsForZSet().incrementScore("zset:test:20240224", "李四", 2);
        redisTemplate.opsForZSet().incrementScore("zset:test:20240224", "王五", 1);
        redisTemplate.opsForZSet().incrementScore("zset:test:20240224", "赵六", 1);

        redisTemplate.opsForZSet().incrementScore("zset:test:20240223", "第一", 7);
        redisTemplate.opsForZSet().incrementScore("zset:test:20240223", "第二", 4);
        redisTemplate.opsForZSet().incrementScore("zset:test:20240223", "王五", 3);
        redisTemplate.opsForZSet().incrementScore("zset:test:20240223", "赵六", 3);


        //redis使用unionAndStore做合并，将结果集放在另一个的key，也就是第三个参数
        redisTemplate.opsForZSet().unionAndStore(null, keys, "employeeRankWeek");

        //查询结果集用employeeRankWeek这个key
        Set<ZSetOperations.TypedTuple<String>> set = redisTemplate.opsForZSet()
                .reverseRangeByScoreWithScores("employeeRankWeek", 0, Double.MAX_VALUE, 0, 3);
        JSONArray jsonArray = JSONObject.parseArray(JSONObject.toJSONString(set));
        for(int i = 0, size = jsonArray.size(); i < size; i++) {
            JSONObject o = JSONObject.parseObject(jsonArray.get(i).toString());
            System.out.println("member：" + o.getString("value") + ", 请求次数：" + o.getLongValue("score"));
        }


        Set<String> set1 = redisTemplate.opsForZSet().reverseRangeByScore("employeeRankWeek", 0, Double.MAX_VALUE, 0 , 5);
        Iterator<String> iterator = set1.iterator();
        while (iterator.hasNext()) {
            System.out.println("value: " + iterator.next().toString());
        }


        List<String> collect = set1.stream().collect(Collectors.toList());
        collect.forEach(System.out::println);



        redisTemplate.delete("list:test:001");
        redisTemplate.opsForList().leftPush("list:test:001", "查询1");
        redisTemplate.opsForList().leftPush("list:test:001", "查询2");
        redisTemplate.opsForList().leftPush("list:test:001", "查询3");
        redisTemplate.opsForList().leftPush("list:test:001", "查询4");
        redisTemplate.opsForList().leftPush("list:test:001", "查询5");
        redisTemplate.opsForList().leftPush("list:test:001", "查询6");
        redisTemplate.opsForList().leftPush("list:test:001", "查询7");
        redisTemplate.opsForList().leftPush("list:test:001", "查询8");

        System.out.println("test001:");
        List<String> range1 = redisTemplate.opsForList().range("list:test:001", 0, 4);
        range1.forEach(System.out::println);

//        redisTemplate.opsForList().remove("list:test:001", 0, "查询6");
//        redisTemplate.opsForList().leftPush("list:test:001", "查询6");
//        redisTemplate.opsForList().trim("list:test:001", 0, 5);

        SessionCallback<Object> callback = new SessionCallback<Object>() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {

                operations.multi();
                operations.opsForList().remove("list:test:001", 0, "查询6");
                operations.opsForList().leftPush("list:test:001", "查询6");
                operations.opsForList().trim("list:test:001", 0, 4);

                return operations.exec();
            }
        };

        Object execute = redisTemplate.execute(callback);
        System.out.println("执行结果：" + execute);


        System.out.println("list test");
        List<String> range = redisTemplate.opsForList().range("list:test:001", 0, 6);
        range.forEach(System.out::println);

    }
}
