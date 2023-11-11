package com.enterprise.controller.permission;

import com.enterprise.common.handler.Result;
import com.enterprise.service.permission.entity.AdminService;
import com.enterprise.util.AuthenticationUtil;
import com.enterprise.util.LogUtil;
import com.enterprise.util.RateLimiterUtil;
import com.enterprise.vo.permission.entity.AdminData;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

/**
 * 负责认证部分的控制层
 *
 * @author PrefersMin
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final AdminService adminService;

    private final AuthenticationUtil authenticationUtil;

    private final RateLimiterUtil rateLimiterUtil;

    @Data
    public static class RegisterRequest {

        private AdminData adminData;

        private String code;

    }

    @PostMapping("/register")
    public Result Register(@RequestBody RegisterRequest request) {

        AdminData adminData = request.getAdminData();

        Result result = authenticationUtil.verifySmsCode(adminData.getAdminPhoneNumber(), request.getCode());
        boolean verify = result.getState();

        if (!verify) {
            return Result.failed().message("注册失败").description(result.getMessage());
        }

        boolean duplicateID = adminService.queryAdminDataCountById(adminData.getAdminAccountId()) > 0;

        if (duplicateID) {
            return Result.failed().message("注册失败").description("该账号已存在");
        }

        boolean duplicatePhone = adminService.queryAdminDataCountByPhone(adminData.getAdminPhoneNumber()) > 0;

        if (duplicatePhone) {
            return Result.failed().message("注册失败").description("该手机号已使用");
        }

        adminData.setAdminCreateTime(new Date(System.currentTimeMillis()));
        adminData.setAdminId(UUID.randomUUID().toString().replace("-", ""));

        boolean insert = adminService.insertAdminData(request.getAdminData());

        if (!insert) {
            return Result.failed().message("注册失败").description("系统异常，请联系管理员");
        }

        return Result.success().message("注册成功").description("即将为您跳转到登录页面");

    }

    @PostMapping("/generateSmsCode")
    public Result generateSmsCode(@RequestParam String phone, HttpServletRequest request) {

        String ip = request.getHeader("Referer");

        if (rateLimiterUtil.isRequestExceededLimit(ip)) {
            String info = "当前IP请求过于频繁，请稍后再试";
            LogUtil.error(info);
            return Result.failed().message("请求失败").description(info);
        }

        // 校验手机号
        if (!authenticationUtil.isValidPhoneNumber(phone)) {
            return Result.failed().message("请求失败").description("手机号不合规");
        }

        return authenticationUtil.generateSmsCode(phone);

    }

}
