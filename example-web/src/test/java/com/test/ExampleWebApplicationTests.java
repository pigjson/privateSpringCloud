package com.test;

import cn.hutool.db.nosql.redis.RedisDS;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExampleWebApplicationTests {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @Test
    public void testReids() {
        redisTemplate.opsForValue().set("test", "1122334455");
        redisTemplate.expire("test", 100, TimeUnit.SECONDS);
    }

    @Test
    public void redis() {
        Jedis jedis = RedisDS.create().getJedis();
        jedis.set("hi", "pizzzz");
        System.out.println(jedis.get("hi"));
    }

}
