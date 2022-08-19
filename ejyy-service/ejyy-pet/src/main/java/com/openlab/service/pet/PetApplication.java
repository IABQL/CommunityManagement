package com.openlab.service.pet;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 张旭
 * \* Date: 2022/8/15
 * \* Time: 15:20
 * \* Description:
 * \
 */
@SpringBootApplication
@MapperScan("com.openlab.service.pet.mapper")
@EnableDiscoveryClient
public class PetApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetApplication.class,args);
    }
}
