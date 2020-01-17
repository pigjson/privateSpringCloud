package com.test.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName DirectRabbitConfig
 * @Description: TODO
 * @Author shibo
 * @Date 2019/12/23
 **/

@Configuration
public class DirectRabbitConfig {
    //队列 起名：TestDirectQueue
    @Bean
    public Queue testDirectQueue() {
        return new Queue("TestDirectQueue", true);  //true 是否持久
    }

    //Direct交换机 起名：TestDirectExchange
    @Bean
    DirectExchange testDirectExchange() {
        return new DirectExchange("TestDirectExchange");
    }

    @Bean
    DirectExchange lonelyDirectExchange() {
        return new DirectExchange("lonelyDirectExchange");
    }

    //绑定  将队列和交换机绑定, 并设置用于匹配键：TestDirectRouting
    @Bean
    Binding bindingDirect() {
        return BindingBuilder.bind(testDirectQueue()).to(testDirectExchange()).with("TestDirectRouting");
    }


}
