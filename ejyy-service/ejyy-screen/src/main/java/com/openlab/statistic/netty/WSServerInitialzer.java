package com.openlab.statistic.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * 连接初始化
 */
public class WSServerInitialzer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) {

        // 获取管道（pipeline）
        ChannelPipeline pipeline = channel.pipeline();
        // websocket 基于http协议，所需要的http 编解码器
        pipeline.addLast(new HttpServerCodec());
        // 分块写数据
        pipeline.addLast(new ChunkedWriteHandler());
        // 对httpMessage（请求头、体） 进行聚合处理后再向后传递
        pipeline.addLast(new HttpObjectAggregator(1024*64));

        // 针对客户端，如果在5s时间内没有向服务端发送读心跳，则产生IdleState.READER_IDLE空闲读事件
        pipeline.addLast(new IdleStateHandler(5,0,5, TimeUnit.SECONDS));
        // 自定义的空闲状态检测的handler，刚才产生的事件交于接下来handler处理
        pipeline.addLast(new HeartBeatHandler());

        /**
         * 负责 websocket 握手建立连接以及控制帧（Close、Ping、Pong）的处理。
         * 文本和二进制数据帧被传递给管道中的下一个处理程序（由您实现）进行处理。
         * 只接受websocket请求
         */
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        // 自定义的handler，处理消息等操作
        pipeline.addLast(new ChatHandler());

    }
}
