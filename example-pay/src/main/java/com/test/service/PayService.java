package com.test.service;

import com.test.event.PayEvent;

/**
 * @ClassName PayService
 * @Description: TODO
 * @Author shibo
 * @Date 2020/3/10
 **/
public interface PayService {
    /***
     * 发送消息
    **/
    void sendMessage(PayEvent payEvent);
    /***
     * 生成本地订单，
     **/
    void creatOrder(PayEvent payEvent);
}
