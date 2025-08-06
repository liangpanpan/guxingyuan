package com.panpan.redis.service;

import com.panpan.redis.entity.Person;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/8/6       create this file
 * </pre>
 */
@Service
public class RedisCacheService {

    private Person person;

//    @Cacheable(cacheNames = "testCache", key = "#token")
    @Cacheable(cacheNames = "testCache", key = "#token")
    public String getCache(String token) {
        System.out.println("execute get cache" + token);
        return "hello world" + token;
    }

    /**
     * 得到Person缓存数据
     * unless = "#result == null"  当返回结果为null的时候，不保存到cache中
     * @param id
     * @return
     */
    @Cacheable(cacheNames = "personCache", key = "#id", unless = "#result == null")
    public Person getUserById(Long id) {
        //如果没走缓存，会打印下面这句话
        System.out.println("=>操作数据库，根据id获取用户信息");
        return this.person;
    }

    /**
     * 将数据保存到缓存中
     *
     * @param person
     * @return
     */
    @CachePut(cacheNames = "personCache", key = "#person.id")
    public Person savePerson(Person person) {
        System.out.println("=>操作数据库保存用户数据");
        this.person = person;
        return person;
    }

    /**
     * 删除缓存中数据
     *
     * @param id
     */
    @CacheEvict(cacheNames = "personCache", key = "#id")
    public void delPerson(Long id) {
        System.out.println("=>操作数据库删除用户数据");
        person = null;
    }


}
