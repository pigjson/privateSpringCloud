package com.test.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.test.event.PayEvent;
import com.test.service.PayService;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @ClassName PayServiceImpl
 * @Description: TODO
 * @Author shibo
 * @Date 2020/3/10
 **/
@Service
public class PayServiceImpl implements PayService {

    @Autowired
    RocketMQTemplate rocketMQTemplate;

    private static int count = 0;
    /***
     * 发送消息
     * @param payEvent*/
    @Override
    public void sendMessage(PayEvent payEvent) {
        //将accountChangeEvent转成json
        JSONObject jsonObject =new JSONObject();
        jsonObject.put("payEvent",payEvent);
        String jsonString = jsonObject.toJSONString();
        //生成message类型
        Message<String> message = MessageBuilder.withPayload(jsonString).build();
        //发送一条事务消息
        rocketMQTemplate.sendMessageInTransaction("producer_pay_my","my_topic",message,null);
    }
    /***
     * 生成本地订单
     * @param payEvent*/
    @Transactional
    @Override
    public void creatOrder(PayEvent payEvent) {
        //幂等判断,通过创建一个数据库表，来判断唯一id是否被重复处理，这里模拟进行
        if(true){
            //TODO
            System.out.println("幂等处理");
        }
        //扣费和本地数据库操作
        System.out.println("扣费交易和记录本次交易");
        //添加事务日志
        System.out.println("在数据库表中，创建一个唯一的id，用于幂等判断");
        //增加异常
        System.out.println("count:"+count);
        count++;
        if(count==5){
            throw new RuntimeException("人为制造异常");
        }

    }
}
