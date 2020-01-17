package com.test.rabbitmq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName FanoutReceiverA
 * @Description: TODO
 * @Author shibo
 * @Date 2019/12/24
 **/
@Component
@RabbitListener(queues = "fanout.A")
public class FanoutReceiverA implements ChannelAwareMessageListener {

    @RabbitHandler
    public void process(Map testMessage) {
        System.out.println("FanoutReceiverA消费者收到消息  : " + testMessage.toString());
    }

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            //因为传递消息的时候用的map传递,所以将Map从Message内取出需要做些处理
            String msg = message.toString();
            System.out.println(msg);
            String[] msgArray = msg.split("'");
            //可以点进Message里面看源码,单引号直接的数据就是我们的map消息数据
            System.out.println(msgArray[1]);
            Map<String, String> msgMap = mapStringToMap(msgArray[1].trim());
            String messageId = msgMap.get("messageId");
            String messageData = msgMap.get("messageData");
            String createTime = msgMap.get("createTime");
            System.out.println("messageId:" + messageId + "  messageData:" + messageData + "  createTime:" + createTime);
            channel.basicAck(deliveryTag, true);
//			channel.basicReject(deliveryTag, true);//为true会重新放回队列
        } catch (Exception e) {
            channel.basicReject(deliveryTag, false);
            e.printStackTrace();
        }
    }

    //{key=value,key=value,key=value} 格式转换成map
    private Map<String, String> mapStringToMap(String str) {
        str = str.substring(1, str.length() - 1);
        String[] strs = str.split(",");
        System.out.println(strs.length);

        Map<String, String> map = new HashMap<String, String>();
        for (String string : strs) {
            System.out.println(string);
            String key = string.split("=")[0].trim();
            String value = string.split("=")[1];
            map.put(key, value);
        }
        return map;
    }
}