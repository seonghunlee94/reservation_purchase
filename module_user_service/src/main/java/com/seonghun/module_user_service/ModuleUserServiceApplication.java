package com.seonghun.module_user_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
@EnableDiscoveryClient
public class ModuleUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModuleUserServiceApplication.class, args);
    }
    
}
