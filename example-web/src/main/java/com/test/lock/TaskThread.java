package com.test.lock;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.UUID;

/**
 * @ClassName TaskThread
 * @Description: TODO
 * @Author shibo
 * @Date 2019/12/16
 **/
public class TaskThread {
    private static JedisPool pool = null;
    private DistributedLock lock = new DistributedLock();

    static {
        JedisPoolConfig config = new JedisPoolConfig();
        // 设置最大连接数
        config.setMaxTotal(500);
        // 设置最大空闲数
        config.setMaxIdle(100);
        // 设置最大等待时间
        config.setMaxWaitMillis(1000 * 100);
        // 在borrow一个jedis实例时，是否需要验证，若为true，则所有jedis实例均是可用的
        config.setTestOnBorrow(true);
        pool = new JedisPool(config, "127.0.0.1", 6379, 300000);
    }



   class MyThread1 extends Thread {
       @Override
       public void run() {
           try {
               String requestId = UUID.randomUUID().toString();
               System.out.println("进入线程1："+requestId);
               lock.lock(pool, "resource", requestId, 3000);
               Thread.sleep(2000);
               lock.unLock(pool, "resource", requestId);
               System.out.println("线程1解锁成功");
           } catch (Exception e) {
               e.printStackTrace();
           }
       }
   }

    class MyThread2 extends Thread {
        @Override
        public void run() {
            try {
                String requestId = UUID.randomUUID().toString();
                System.out.println("进入线程3："+requestId);
                lock.lock(pool, "resource", requestId, 3000);
                lock.unLock(pool, "resource", requestId);
                System.out.println("线程2解锁成功");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    class MyThread3 extends Thread {
        @Override
        public void run() {
            try {

                String requestId = UUID.randomUUID().toString();
                System.out.println("进入线程3："+requestId);
                if(lock.tryLock(pool, "resource", requestId, 3000)){
                    System.out.println("线程3获得了锁进行业务处理");
                    Thread.sleep(2000);
                    lock.unLock(pool, "resource", requestId);
                    System.out.println("线程3解锁成功");
                }else{
                    System.out.println("线程3没有获得到锁，其他线程正在执行");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class MyThread4 extends Thread {
        @Override
        public void run() {
            try {

                String requestId = UUID.randomUUID().toString();
                System.out.println("进入线程4："+requestId);
                if(lock.tryLock(pool, "resource", requestId, 3000)){
                    System.out.println("线程4获得了锁进行业务处理");
                    Thread.sleep(2000);
                    lock.unLock(pool, "resource", requestId);
                    System.out.println("线程4解锁成功");
                }else{
                    System.out.println("线程4没有获得到锁，其他线程正在执行");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        TaskThread task = new TaskThread();
        TaskThread.MyThread1 t1 = task.new MyThread1();
        TaskThread.MyThread2 t2 = task.new MyThread2();
        t1.start();
        t2.start();

//        TaskThread task = new TaskThread();
//        TaskThread.MyThread3 t3 = task.new MyThread3();
//        TaskThread.MyThread4 t4 = task.new MyThread4();
//        t3.start();
//        t4.start();


    }
}
