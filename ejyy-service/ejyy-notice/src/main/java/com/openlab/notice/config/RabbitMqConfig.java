package com.openlab.notice.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    public static String EXCHANGE_NAME = "fanout";
    public static String QUEUE_NAME = "notice_queue";

    // 交换机
    @Bean
    public Exchange bootExchange(){
        return ExchangeBuilder.fanoutExchange(EXCHANGE_NAME).build();
    }

    // 队列
    @Bean
    public Queue bootQueue(){
        return QueueBuilder.durable(QUEUE_NAME).build();
    }

    // 队列交换机绑定
    @Bean
    public Binding bindQueueExchange(@Qualifier("bootQueue") Queue queue, @Qualifier("bootExchange") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("").noargs();
    }
}