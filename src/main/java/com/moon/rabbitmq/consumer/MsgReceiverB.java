package com.moon.rabbitmq.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Author : moon
 * Date  : 2018/11/27 11:19
 * Description : Class for
 */
@Component
@RabbitListener(queues = "QUEUE_B")
public class MsgReceiverB {

    private static final Logger LOG = LoggerFactory.getLogger(MsgReceiverB.class);

    @RabbitHandler
    public void process(String content) {
        LOG.info("接收处理队列B中的消息：" + content);
    }
}
