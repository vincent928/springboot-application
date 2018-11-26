package com.moon.rabbitmq.producer;

import com.moon.enums.rabbitmq.ExchangeEnum;
import com.moon.enums.rabbitmq.RoutingKeyEnum;
import org.springframework.amqp.rabbit.core.RabbitTemplate;


public interface IMsgProducer extends RabbitTemplate.ConfirmCallback {

    /**
     * 发送消息到rabbitmq消息队列
     * @param msg   消息内容
     * @param exchangeEnum  交换配置枚举
     * @param routingKeyEnum     队列配置枚举
     * @throws Exception
     */
    void send(Object msg, ExchangeEnum exchangeEnum, RoutingKeyEnum routingKeyEnum) throws Exception;



}
