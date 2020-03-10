package com.test.message;

import com.alibaba.fastjson.JSONObject;
import com.test.event.PayEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName MsgConsumer
 * @Description: TODO
 * @Author shibo
 * @Date 2020/3/10
 **/
@Component
@Slf4j
@RocketMQMessageListener(consumerGroup = "producer_group_ballOrder",topic = "my_topic")
public class MsgConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        try {
            log.info("开始消费消息:{}", message);
            //解析消息
            JSONObject jsonObject = JSONObject.parseObject(message);
            String payEventString = jsonObject.getString("payEvent");
            //转成AccountChangeEvent
            PayEvent payEvent = JSONObject.parseObject(payEventString, PayEvent.class);
            System.out.println("调用service 处理业务");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("异常了");
        }
    }
}
