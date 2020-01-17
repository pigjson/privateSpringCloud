package com.test;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableEurekaClient
public class ExampleActivityApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleActivityApplication.class, args);
    }

}
