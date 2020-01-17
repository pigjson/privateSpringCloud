package com.test;


import com.test.dao.BallRepository;
import com.test.dao.MyBallRepository;
import com.test.entity.Ball;
import com.test.entity.BallHero;
import com.test.service.MyInterface;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ExampleWebApplicationTests {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private BallRepository ballRepository;
    @Resource
    private MyBallRepository myBallRepository;

    @Test
    public void testReids() {
        redisTemplate.opsForValue().set("test", "1122334455");
        redisTemplate.expire("test", 100, TimeUnit.SECONDS);
    }


    @Test
    public void testLock() {
        MyInterface myInterface = e -> e + 1;
        myInterface.testStr("123");
    }

    @Test
    public void testJpa() {
//        List<Ball> balls = ballRepository.findByBlue("05");
//        System.out.println(balls.size());
//        List<Ball> balls = myBallRepository.getAllBall();
//        System.out.println(balls.size());
        List<BallHero> bh = myBallRepository.getHB();
        System.out.println(bh.size());
    }


}
