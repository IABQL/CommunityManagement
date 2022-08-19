package com.openlab.statistic.rabbit;

import com.openlab.statistic.netty.ChatHandler;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Iterator;

@Component
public class RabbitConsumer {
    @RabbitListener(queues = "notice_queue")
    public void consumer(Message message){
        String msg = new String(message.getBody());
        dealMessage(msg);
    }

    private void dealMessage(String message){
        Iterator<Channel> channelIterator = ChatHandler.users.iterator();
        while (channelIterator.hasNext()) {
            Channel channel = channelIterator.next();
            channel.writeAndFlush(new TextWebSocketFrame(message));
        }
    }
}
