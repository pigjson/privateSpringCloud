package com.test.lock;

import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @ClassName Lock
 * @Description: TODO
 * @Author shibo
 * @Date 2019/12/17
 **/
public interface Lock {
    /**
     * 加锁
     */
    boolean lock(String key, String value);

    /**
     * 解锁
     */
    void unlock(String key, String value);

    /**
     * 解锁
     */
    void unlock(String key);

    /***
     * @MethodName: spinLock
     * @Description: 自旋锁
     * @Date: 2019/12/17
     **/
    void spinLock(String key, String value);
}
