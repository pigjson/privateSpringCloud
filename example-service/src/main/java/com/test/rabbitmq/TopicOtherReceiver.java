package com.test.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @ClassName TopicOtherReceiver
 * @Description: TODO
 * @Author shibo
 * @Date 2019/12/27
 **/
@Component
@RabbitListener(queues = "topic.other")
public class TopicOtherReceiver {
    @RabbitHandler
    public void process(Map testMessage) {
        System.out.println("TopicOtherReceiver消费者收到消息  : " + testMessage.toString());
    }
}
