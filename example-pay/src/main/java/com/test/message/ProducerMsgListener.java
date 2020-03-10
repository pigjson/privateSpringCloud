package com.test.message;

import com.alibaba.fastjson.JSONObject;
import com.test.event.PayEvent;
import com.test.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName ProducerMsgListener
 * @Description: TODO
 * @Author shibo
 * @Date 2020/3/10
 **/
@Component
@Slf4j
@RocketMQTransactionListener(txProducerGroup = "producer_pay_my")
public class ProducerMsgListener implements RocketMQLocalTransactionListener {

    @Autowired
    private PayService payService;


    //事务消息发送后的回调方法，当消息发送给mq成功，此方法被回调
    @Transactional
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        System.out.println("事务消息发送成功");
        try {
            //解析message，转成payEvent
            String messageString = new String((byte[]) message.getPayload());
            JSONObject jsonObject = JSONObject.parseObject(messageString);
            String payEventString = jsonObject.getString("payEvent");
            //payEvent（json）转成payEvent
            PayEvent payEvent = JSONObject.parseObject(payEventString, PayEvent.class);
            //执行本地事务，扣减金额
            payService.creatOrder(payEvent);
            //当返回RocketMQLocalTransactionState.COMMIT，自动向mq发送commit消息，mq将消息的状态改为可消费
            System.out.println("COMMIT");
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            System.out.println("ROLLBACK");
            e.printStackTrace();
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }
    //事务状态回查，在未收到消息确认的时候，进行状态回查，根据事务id
    @Transactional
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        //解析message，转成payEvent
        String messageString = new String((byte[]) message.getPayload());
        JSONObject jsonObject = JSONObject.parseObject(messageString);
        String payEventString = jsonObject.getString("payEvent");
        //payEvent（json）转成payEvent
        PayEvent payEvent = JSONObject.parseObject(payEventString, PayEvent.class);
        //事务id
        String txNo = payEvent.getTxNo();
        System.out.println("本地数据库查询txNo是否存在:"+txNo);
        //人为制造
        if(!"123456".equals(txNo)){
            return RocketMQLocalTransactionState.COMMIT;
        }else{
            return RocketMQLocalTransactionState.UNKNOWN;
        }
    }
}
