package com.test.controller;

import com.test.lock.Lock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试
 */
@Controller
@RequestMapping("/exampleController")
public class ExampleController {

    private final static Logger logger = LoggerFactory.getLogger(ExampleController.class);

    @Autowired
    RabbitTemplate rabbitTemplate;  //使用RabbitTemplate,这提供了接收/发送等等方法

    @Autowired
    private Lock lock;

    @RequestMapping("/testPost")
    @ResponseBody
    public Object testPost() {
        logger.info("我是service，我运行了");
        Map<String, Object> map = new HashMap<String, Object>(1);
        map.put("json", "mysjson");
        return map;
    }


    @RequestMapping("/testLock1")
    @ResponseBody
    public Object testLock1(@RequestBody String type) throws InterruptedException {
        logger.info("testLock1----->自旋锁:" + type);
        lock.spinLock("resource", System.currentTimeMillis() + 10000 + "");
        logger.info("testLock1----->自旋锁:" + type + "请求在工作");
        if ("111".equals(type)) {
            Thread.sleep(3000);
        }
        lock.unlock("resource");
        logger.info("testLock1----->自旋锁解锁:" + type);
        return true;
    }

    @RequestMapping("/testLock2")
    @ResponseBody
    public Object testLock2(@RequestBody String type) throws InterruptedException {
        logger.info("testLock2----->普通锁:" + type);
        if (lock.lock("resource", System.currentTimeMillis() + 10000 + "")) {
            logger.info("testLock2----->目前无锁:" + type);
            Thread.sleep(3000);
            logger.info("testLock2----->自旋锁:" + type + "请求在工作");
            lock.unlock("resource");
            logger.info("testLock2----->普通锁解锁:" + type);
        } else {
            logger.info("testLock2----->锁在被占用:" + type);
        }
        return true;
    }

}
