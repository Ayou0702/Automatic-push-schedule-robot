package com.enterprise.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域配置
 *
 * @author Iwlthxcl
 * @version 1.0
 * @time 2023/3/8 16:40
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings (CorsRegistry registry) {
        registry
                // 放行路径
                .addMapping("/**")
                // 放行方法
                .allowedMethods("*")
                // 预检间隔
                .maxAge(168000)
                // 允许头部设置
                .allowedHeaders("*").allowedOriginPatterns("*")
                // 发送Cookie
                .allowCredentials(true);
    }

}
