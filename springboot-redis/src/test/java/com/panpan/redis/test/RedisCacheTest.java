package com.panpan.redis.test;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.panpan.redis.RedisApplication;
import com.panpan.redis.entity.Person;
import com.panpan.redis.service.RedisCacheService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/8/6       create this file
 * </pre>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisApplication.class)
public class RedisCacheTest {

    @Autowired
    private RedisCacheService userService;

    @Test
    public void testRedisCache() {
        Person person = new Person(1L, "陈大侠", "18", "男");
        System.out.println("插入用户，新增缓存");
        userService.savePerson(person);
        System.out.println("第一次获取用户，存在缓存就从缓存返回,不存在就从数据库取");
        Person dbPerson = userService.getUserById(1L);
        System.out.println(dbPerson);
        System.out.println("更新用户,更新缓存");
        person.setAge("88");
        userService.savePerson(person);
        System.out.println("第二次获取用户，看缓存是否有变化");
        dbPerson = userService.getUserById(1L);
        System.out.println(dbPerson);
        System.out.println("删除用户,删除缓存");
//        userService.delPerson(1L);
        System.out.println("第三次再次获取用户，看缓存是否有变化");
        dbPerson = userService.getUserById(1L);
        System.out.println(dbPerson);
    }


    @Test
    public void testFastJson() {
        Person person = new Person(1L, "陈大侠", "18", "男");

        System.out.println(JSON.toJSONString(person, JSONWriter.Feature.WriteClassName));

    }


}
