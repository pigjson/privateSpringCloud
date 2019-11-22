package com.test;

import cn.hutool.db.nosql.redis.RedisDS;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExampleWebApplicationTests {
    @Value("${testpara}")
    private  String testpara;
    @Test
    public void contextLoads() {
        System.out.println(testpara);
    }

    @Test
    public void redis(){
        Jedis jedis = RedisDS.create().getJedis();
        jedis.set("hi","pizzzz");
        System.out.println(jedis.get("hi"));
    }

}
