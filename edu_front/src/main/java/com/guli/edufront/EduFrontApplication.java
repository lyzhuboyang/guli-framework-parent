package com.guli.edufront;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient//服务注册
@EnableFeignClients//服务调用
public class EduFrontApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduFrontApplication.class, args);
    }
}
