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

    private EventLoopGroup mainGroup;
    private EventLoopGroup subGroup;
    private ServerBootstrap server;
    private ChannelFuture future;

    private WebSocketServer() {
        mainGroup = new NioEventLoopGroup();
        subGroup = new NioEventLoopGroup();
        server = new ServerBootstrap();
        server.group(mainGroup, subGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new WSServerInitialzer());// 添加初始化事件
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
