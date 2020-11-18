package com.guli.edu.eduvod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EduVodApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduVodApplication.class, args);
    }
}
