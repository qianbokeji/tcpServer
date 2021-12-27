package com.qianbo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = "com.qianbo.mapper")
public class QianboTcpserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(QianboTcpserverApplication.class, args);
    }

}
