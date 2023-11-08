package com.enterprise.controller.control;

import cn.dev33.satoken.stp.SaLoginConfig;
import cn.dev33.satoken.stp.StpUtil;
import com.enterprise.common.handler.Result;
import com.enterprise.entity.Admin;
import com.enterprise.entity.Permission;
import com.enterprise.entity.vo.AdminVo;
import com.enterprise.service.AdminService;
import com.enterprise.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 负责认证的Controller
 *
 * @author PrefersMin
 * @version 1.0
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AutoController {

    private final AdminService adminService;
    private final PermissionService permissionService;

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

    @GetMapping("/login_info")
    public Result getLoginAdminInfo(HttpServletRequest httpServletRequest) {
        // 获取到请求头中的token
        String token = httpServletRequest.getHeader("token");
        // 根据token获取管理员ID
        String adminId = (String) StpUtil.getLoginIdByToken(token);
        if (adminId != null) {
            // 调用方法查询该管理员所拥有的权限列表
            AdminVo adminVo = adminService.findAdminAndRoleById(adminId);
            adminVo.setPassWord("");
            // 根据用户读取角色
            List<Permission> permissionList = permissionService.findMenuByRoleId(adminVo.getRoleId(), true);
            return Result.success().data("permissionList", permissionList).data("loginAdmin", adminVo);
        }
        return Result.failed().message("无效的Token!");
    }

}
