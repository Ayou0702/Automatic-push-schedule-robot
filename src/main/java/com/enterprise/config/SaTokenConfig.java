package com.enterprise.config;

import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import cn.dev33.satoken.jwt.StpLogicJwtForStateless;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.stp.StpUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SaTokenConfig implements WebMvcConfigurer {
    // Sa-Token 整合 jwt (Stateless 无状态模式)
    @Bean
    public StpLogic getStpLogicJwt() {
        return new StpLogicJwtForStateless();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 的路由拦截器，自定义认证规则
        registry.addInterceptor(new SaRouteInterceptor((req, res, handler) -> {
            String token = req.getHeader("token");
            if (Strings.isEmpty(token)){
                token = req.getParam("token");
            }
            String adminId = (String) StpUtil.getLoginIdByToken(token);
            StpUtil.login(adminId);
            SaRouter.match("/admin/**", r -> StpUtil.checkPermission("system:admin"));
            SaRouter.match("/role/**", r -> StpUtil.checkPermission("system:role"));
            SaRouter.match("/permission/**", r -> StpUtil.checkPermission("system:permission"));
        })).addPathPatterns("/**").excludePathPatterns("/auth/**");
    }
}

