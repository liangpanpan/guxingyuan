package com.panpan.redis.controller;

import com.panpan.redis.service.RedisCacheService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/8/6       create this file
 * </pre>
 */
@RestController
public class RedisCacheController {

    @Resource
    private RedisCacheService redisCacheService;

    @RequestMapping("/getCache")
    public String getCache(String token) {
        System.out.println("controller get cache");
        return redisCacheService.getCache(token);
    }






}
