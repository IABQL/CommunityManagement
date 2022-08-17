package com.openlab.notice.rabbit;

import com.openlab.notice.config.RabbitMqConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitProvider {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(String msg){
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_NAME,"",msg);
    }
}
