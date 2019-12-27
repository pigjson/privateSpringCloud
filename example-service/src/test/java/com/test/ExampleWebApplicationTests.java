package com.test;


import com.test.service.MyInterface;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ExampleWebApplicationTests {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void testReids(){
        redisTemplate.opsForValue().set("test", "1122334455");
        redisTemplate.expire("test",100, TimeUnit.SECONDS);
    }


    @Test
    public void testLock(){
        MyInterface myInterface = e-> e+1;
        myInterface.testStr("123");
    }




}
