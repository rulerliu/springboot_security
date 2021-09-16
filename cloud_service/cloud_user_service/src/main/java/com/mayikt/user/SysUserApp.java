package com.mayikt.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = {"com.mayikt"})
@MapperScan(basePackages = "com.mayikt.user.mapper")
@EnableDiscoveryClient
//@EnableFeignClients("com.mayikt.user.feign")
public class SysUserApp {

    public static void main(String[] args) {
        SpringApplication.run(SysUserApp.class, args);
    }
}
