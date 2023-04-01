package com.enterprise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 项目启动类
 *
 * @author Iwlthxcl
 * @version 1.1
 */
@SpringBootApplication()
@EnableScheduling
public class EnterpriseWxPushApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnterpriseWxPushApplication.class, args);
    }

}
