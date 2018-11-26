package com.moon.rabbitmq.producer;

import com.moon.enums.rabbitmq.ExchangeEnum;
import com.moon.enums.rabbitmq.RoutingKeyEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Author : moon
 * Date  : 2018/11/26 13:57
 * Description : Class for 消息的生产者
 */
@Component
public class MsgProducer implements IMsgProducer {

    private static final Logger LOG = LoggerFactory.getLogger(MsgProducer.class);
    //由于scope属性为SCOPE_PROTOTYPE所以不能自动注入
    private RabbitTemplate rabbitTemplate;

    /**
     * 使用构造方法注入
     *
     * @param rabbitTemplate
     */
    @Autowired
    public MsgProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        //rabbitTemplate如果是单例的话，那回调就是最后设置的内容
        rabbitTemplate.setConfirmCallback(this);
    }

    @Override
    public void send(Object msg, ExchangeEnum exchangeEnum, RoutingKeyEnum routingKeyEnum) throws Exception {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(exchangeEnum.getValue(), routingKeyEnum.getValue(), msg, correlationId);
    }

    /**
     * 回调
     *
     * @param correlationData
     * @param b
     * @param s
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        LOG.info("回调ID:" + correlationData);
        if (b) {
            LOG.info("消息成功消费");
        } else {
            LOG.info("消息消费失败:" + s);
        }
    }


}
