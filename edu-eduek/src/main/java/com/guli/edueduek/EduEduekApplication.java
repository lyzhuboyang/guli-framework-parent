package com.guli.edueduek;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EduEduekApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduEduekApplication.class, args);
    }

}
