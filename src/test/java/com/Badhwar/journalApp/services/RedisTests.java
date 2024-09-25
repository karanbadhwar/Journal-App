package com.Badhwar.journalApp.services;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Disabled
    @Test
    void testRedis()
    {
        //Already Done
//        redisTemplate.opsForValue().set("email", "abc@gmail");

        redisTemplate.opsForValue().get("name");
        redisTemplate.opsForValue().get("email");
        int a = 1;
    }
}
