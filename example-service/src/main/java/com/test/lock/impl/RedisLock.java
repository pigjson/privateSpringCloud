package com.test.lock.impl;

import com.test.lock.Lock;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @ClassName RedisLock
 * @Description: TODO
 * @Author shibo
 * @Date 2019/12/17
 **/
@Component
public class RedisLock implements Lock {
    private final static Logger log = LoggerFactory.getLogger(RedisLockBak.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 加锁
     */
    @Override
    public boolean lock(String key, String value) {
        if (stringRedisTemplate.opsForValue().setIfAbsent(key, value)) {
            return true;
        }
        String currentValue = stringRedisTemplate.opsForValue().get(key);
        if (!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue) < System.currentTimeMillis()) {
            String oldvalue = stringRedisTemplate.opsForValue().getAndSet(key, value);
            if (!StringUtils.isEmpty(oldvalue) && oldvalue.equals(currentValue)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 解锁
     */
    @Override
    public void unlock(String key) {
        stringRedisTemplate.opsForValue().getOperations().delete(key);
    }


    /**
     * 解锁
     */
    @Override
    public void unlock(String key, String value) {
        try {
            String currentValue = stringRedisTemplate.opsForValue().get(key);
            if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)) {
                stringRedisTemplate.opsForValue().getOperations().delete(key);
            }
        } catch (Exception e) {
            log.error("解锁异常");
        }
    }

    /***
     * @MethodName: spinLock
     * @Description: 自旋锁
     * @Date: 2019/12/17
     */
    @Override
    public void spinLock(String key, String value) {
        while (true) {
            if (lock(key, value)) {
                System.out.println("lock  " + Thread.currentThread().getName() + " value:" + value);
                return;
            }
        }
    }


}
