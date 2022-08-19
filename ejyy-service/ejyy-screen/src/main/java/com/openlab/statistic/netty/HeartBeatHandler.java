package com.openlab.statistic.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * 用于检测channel 的心跳handler
 * ChannelDuplexHandler双向通道
 */
@Slf4j
@Component
public class HeartBeatHandler extends ChannelDuplexHandler {

    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        if(evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent)evt;
            if(event.state()== IdleState.READER_IDLE){
                log.debug("读空闲......");
            }else if(event.state() == IdleState.WRITER_IDLE) {
                log.debug("写空闲......");
            }else if(event.state()== IdleState.ALL_IDLE){
                log.info("长时间没有读写消息，即将断开......");
                Channel channel = ctx.channel();
                //资源释放
                ChatHandler.users.remove(channel);
                channel.close();
            }
        }
    }
}
