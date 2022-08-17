package com.openlab.service.epidemic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@MapperScan("com.openlab.service.epidemic.mapper")
@EnableDiscoveryClient
public class EpidemicApplication {
    public static void main(String[] args) {
        SpringApplication.run(EpidemicApplication.class,args);
    }
}
