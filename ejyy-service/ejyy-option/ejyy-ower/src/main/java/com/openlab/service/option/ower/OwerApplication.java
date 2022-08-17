package com.openlab.service.option.ower;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.openlab.service.option.ower.mapper")
public class OwerApplication {
    public static void main(String[] args) {
        SpringApplication.run(OwerApplication.class,args);
    }
}
