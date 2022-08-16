package com.openlab.statistic.config;

import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NettyConfig {
    @Bean
    public HttpServerCodec httpServerCodec(){
        return new HttpServerCodec();
    }

    @Bean
    public ChunkedWriteHandler chunkedWriteHandler(){
        return new ChunkedWriteHandler();
    }

    @Bean
    public HttpObjectAggregator httpObjectAggregator(){
        return new HttpObjectAggregator(1024*64);
    }

    @Bean
    public WebSocketServerProtocolHandler webSocketServerProtocolHandler(){
        return new WebSocketServerProtocolHandler("/ws");
    }
}
