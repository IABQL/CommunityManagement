package com.openlab.statistic.netty;

import com.alibaba.fastjson.JSONObject;
import com.openlab.statistic.entity.MsgActionEnum;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;


/**
 * 用于处理消息的handler
 */
@Slf4j
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {


    // 用于记录和管理所有客户端的channel
    public static ChannelGroup users = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        String content = msg.text();
        log.info(content);

        DataContent dataContent = JSONObject.parseObject(content, DataContent.class);
        Integer action = dataContent.getAction();
        Channel channel =  ctx.channel();

        if(action == MsgActionEnum.CONNECT.type){
            // websocket 第一次连接
            String senderId = dataContent.getUserId();
            // 本地存储
            UserChanelRel.put(senderId,channel);
        }else if(action == MsgActionEnum.KEEPALIVE.type){
            // 心跳类型的消息
            log.info("收到来自channel 为【"+channel+"】的心跳包");
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        users.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        UserChanelRel.remove(ctx.channel());
        users.remove(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        // 发生了异常后关闭连接，同时从channelgroup移除
        ctx.channel().close();
        UserChanelRel.remove(ctx.channel());
        users.remove(ctx.channel());
    }
}
