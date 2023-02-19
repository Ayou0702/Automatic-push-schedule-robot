package com.enterprise;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.enterprise"})
@MapperScan("com.enterprise.mapper")
@EnableScheduling
public class EnterpriseWxPushApplication {

    public static void main (String[] args) {
        SpringApplication.run(EnterpriseWxPushApplication.class, args);
    }

}
