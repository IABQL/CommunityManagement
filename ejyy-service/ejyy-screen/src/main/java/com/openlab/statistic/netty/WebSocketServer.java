package com.openlab.statistic.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * websocket服务端
 */
@Component
@Slf4j
public class WebSocketServer {

    private static final WebSocketServer instance = new WebSocketServer();

    private EventLoopGroup bossGroup;
    private EventLoopGroup workGroup;
    private ServerBootstrap server;
    private ChannelFuture future;

    private WebSocketServer() {
        bossGroup = new NioEventLoopGroup(1);
        workGroup = new NioEventLoopGroup();
        server = new ServerBootstrap();
        server.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new WSServerInitialzer());// 添加初始化事件，处理客服端连接的socketChannel
    }

    public static WebSocketServer getInstance() {
        return instance;
    }

    public void start() {
        try {
            this.future = server.bind(9081).sync();
        }catch (Exception e){

        }

        if (future.isSuccess()) {
            log.info("启动 Netty 成功");
        }
    }
}
