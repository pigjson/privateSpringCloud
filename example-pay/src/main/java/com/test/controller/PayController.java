package com.test.controller;

import com.test.event.PayEvent;
import com.test.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @ClassName PayController
 * @Description: TODO
 * @Author shibo
 * @Date 2020/3/10
 **/
@RestController
public class PayController {

    @Autowired
    private PayService payService;

    @GetMapping(value = "/pay")
    public String pay(){
        //创建一个事务id，作为消息内容发到mq
        String tx_no = UUID.randomUUID().toString();
        PayEvent payEvent = new PayEvent();
        payEvent.setAccountNo("123456");
        payEvent.setAmount(0.01);
        payEvent.setTxNo(tx_no);
        System.out.println("发送消息tx_no："+tx_no);
        //发送消息
        payService.sendMessage(payEvent);
        return "生成订单发送消息成功";
    }
}
