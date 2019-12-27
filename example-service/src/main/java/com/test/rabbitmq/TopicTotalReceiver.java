package com.test.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @ClassName TopicTotalReceiver
 * @Description: TODO
 * @Author shibo
 * @Date 2019/12/24
 **/
@Component
//@RabbitListener(queues = "topic.woman")
public class TopicTotalReceiver {

    @RabbitListener(queues = "topic.woman")
    public void woman(Map testMessage) {
        System.out.println("TopicTotalReceiver消费者收到消息  : " + testMessage.toString());
    }

}