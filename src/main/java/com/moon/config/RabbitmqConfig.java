package com.moon.config;

import com.moon.enums.rabbitmq.ExchangeEnum;
import com.moon.enums.rabbitmq.QueueEnum;
import com.moon.enums.rabbitmq.RoutingKeyEnum;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Broker:它提供一种传输服务，它的角色就是维护一条生产者到消费者的路线，保证数据能按照指定的方式进行传输
 * Exchange:消息交换机，它指定消息按什么规则，路由到哪个队列。
 * Queue:消息的载体，每个消息都会被投到一个或多个队列
 * Binding:绑定，它的作用就是把exchange和queue按照路由规则绑定起来
 * Routing Key:路由关键字，exchange根据这个关键字进行消息投递
 * vhost:虚拟主机，一个broker里有多个vhost，用作不同用户的权限分离
 * Producer:消息生产者，就是投递消息的程序
 * Consumer:消息消费者，就是接受消息的程序
 * Channel:消息通道，在客户端的每个链接里，可建立多个channel
 * <p>
 * <p>
 * 1、配置交换实例
 * 配置DirectExchange实例对象，为交换设置一个名称，引用ExchangeEnum枚举配置的交换名称，消息提供
 * 者与消息消费者的交换名称必须一致才具备的第一步的通讯基础
 * <p>
 * 2、配置队列实例
 * 配置Queue实例对象，为消息队列设置一个名称，引用QueueEnum枚举配置的队列名称，队列的名称同样也是
 * 提供者与消费者之间的通讯基础
 * <p>
 * 3、绑定队列实例到交换实例
 * 配置Binding实例对象，消息绑定的目的就是将Queue实例绑定到Exchange上，并且通过设置的路由Key进行消息
 * 转发，配置了路由Key后，只有符合路由配置的消息才会被转发到绑定交换上的消息队列
 */
@Configuration
public class RabbitmqConfig {

    @Value("${spring.rabbitmq.host}")
    private String host;
    @Value("${spring.rabbitmq.port}")
    private int port;
    @Value("${spring.rabbitmq.username}")
    private String username;
    @Value("${spring.rabbitmq.password}")
    private String password;


    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        //配置虚拟空间地址
        connectionFactory.setVirtualHost("/");
        //配置发布消息回调
        connectionFactory.setPublisherConfirms(true);
        return connectionFactory;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    //必须是prototype类型
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        return rabbitTemplate;
    }

    /**
     * 针对消费者配置
     * 1.设置交换机类型
     * 2.将队列绑定到交换机
     * FanoutExchange：将消息分发到所有的绑定队列，无routingKey的概念
     * HeadersExchange：通过添加属性key-value匹配
     * DirectExchange：通过routingKey分发到指定队列
     * TopicExchange：多关键字匹配
     *
     * @return
     */
    @Bean
    public DirectExchange defaultExchange() {
        return new DirectExchange(ExchangeEnum.EXCHANGE_A.getValue());
    }

    /**
     * 获取队列A
     *
     * @return
     */
    @Bean
    public Queue queueA() {
        //队列持久
        return new Queue(QueueEnum.QUEUE_A.getValue(), true);
    }

    @Bean
    public Queue queueB() {
        return new Queue(QueueEnum.QUEUE_B.getValue(), true);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queueA()).to(defaultExchange()).with(RoutingKeyEnum.ROUTINGKEY_A.getValue());
    }

    /**
     * 一个交换机可以绑定多个消息队列，消息通过一个交换机，可以分发到不同队列中去
     *
     * @return
     */
    @Bean
    public Binding bindingB() {
        return BindingBuilder.bind(queueB()).to(defaultExchange()).with(RoutingKeyEnum.ROUTINGKEY_B.getValue());
    }

}
