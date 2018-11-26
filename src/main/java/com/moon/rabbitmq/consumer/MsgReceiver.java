package com.moon.rabbitmq.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Author : moon
 * Date  : 2018/11/26 14:57
 * Description : Class for 消费者
 *
 * @RabbitListener: RabbitMq队列消息监听注解，该注解配置监听queues内的队列名称列表，可以配置多个。队列名称对应QUEUE
 * @RabbitHandler: RabbitMq消息处理方法，该方法参数要与provider发送消息时类型保持一致，否则无法自动调用消费方法
 */
@Component
@RabbitListener(queues = "QUEUE_A")
public class MsgReceiver {

    private static final Logger LOG = LoggerFactory.getLogger(MsgReceiver.class);

    @RabbitHandler
    public void process(String content) {
        LOG.info("接收处理队列A中的消息：" + content);
        //业务逻辑处理
    }


}
