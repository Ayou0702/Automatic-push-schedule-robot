package com.enterprise;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 项目启动类，配置了扫描路径和自动任务
 *
 * @author Iwlthxcl
 * @version 1.0
 * @time 2023/3/8 17:00
 */
@SpringBootApplication(scanBasePackages = {"com.enterprise"})
@MapperScan("com.enterprise.mapper")
@EnableScheduling
public class EnterpriseWxPushApplication {

    public static void main (String[] args) {
        SpringApplication.run(EnterpriseWxPushApplication.class, args);
    }

}
