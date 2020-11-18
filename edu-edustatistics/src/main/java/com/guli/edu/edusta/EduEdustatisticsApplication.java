package com.guli.edu.edusta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class EduEdustatisticsApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduEdustatisticsApplication.class, args);
    }

}
