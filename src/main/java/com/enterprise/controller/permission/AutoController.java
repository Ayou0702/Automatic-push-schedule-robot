package com.enterprise.controller.permission;

import cn.dev33.satoken.stp.SaLoginConfig;
import cn.dev33.satoken.stp.StpUtil;
import com.enterprise.common.handler.Result;
import com.enterprise.service.permission.entity.AdminService;
import com.enterprise.vo.permission.entity.Admin;
import com.enterprise.vo.pojo.AdminVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 负责认证的Controller
 *
 * @author PrefersMin
 * @version 1.1
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AutoController {

    private final AdminService adminService;

    /**
     * 管理员登录
     *
     * @param admin 仅包含用户名和密码的管理员实体类
     * @return 统一接口返回值
     */
    @PostMapping("/login")
    public Result Login(@RequestBody Admin admin) {
        AdminVo adminVo = adminService.Login(admin.getUserName(), admin.getPassWord());
        String tokenValue = null;
        if (adminVo != null) {
            // 登录认证
            StpUtil.login(adminVo.getId(), SaLoginConfig.setExtra("adminId", adminVo.getId()));
//            StpUtil.getPermissionList();
//            System.out.println(StpUtil.getPermissionList());
            // 设置Token名称和值
            tokenValue = StpUtil.getTokenValue();
        }
        return Result.success().data("tokenValue", tokenValue);
    }

    @GetMapping("/islogin")
    public Result isLogin() {
        return Result.success().data("isLogin", StpUtil.getTokenValue());
    }

}
