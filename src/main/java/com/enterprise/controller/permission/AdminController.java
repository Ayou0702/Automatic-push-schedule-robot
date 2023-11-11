package com.enterprise.controller.permission;

import com.enterprise.common.handler.Result;
import com.enterprise.service.permission.entity.AdminService;
import com.enterprise.vo.permission.entity.AdminData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * (Admin)表控制层
 *
 * @author PrefersMin
 * @since 2023-11-11 20:48:56
 */
@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {

    /**
     * (Admin)表服务接口
     */
    private final AdminService adminService;

    /**
     * 查询所有数据
     *
     * @return 所有数据
     */
    @GetMapping("/getAllAdminData")
    public Result getAllAdminData() {

        List<AdminData> adminList = adminService.getAllAdmin();

        if (adminList == null) {
            return Result.failed().message("加载数据失败");
        }

        return Result.success().message("加载数据成功").data("adminList", adminList);

    }

    /**
     * 通过主键ID查询单条数据
     *
     * @param adminId 主键ID
     * @return 单条数据
     */
    @PostMapping("/queryAdminDataById")
    public Result queryAdminDataById(String adminId) {
        return Result.success().data("data", adminService.queryAdminDataById(adminId));
    }

    /**
     * 新增数据
     *
     * @param admin 实体
     * @return 新增结果
     */
    @PostMapping("/insertAdminData")
    public Result insertAdminData(AdminData admin) {

        boolean result = adminService.insertAdminData(admin);

        if (result) {
            return Result.success().message("数据新增成功");
        } else {
            return Result.failed().message("数据新增失败");
        }

    }

    /**
     * 编辑数据
     *
     * @param admin 实体
     * @return 编辑结果
     */
    @PostMapping("/updateAdminData")
    public Result updateAdminData(AdminData admin) {

        boolean result = adminService.updateAdminData(admin);

        if (result) {
            return Result.success().message("数据修改成功");
        } else {
            return Result.failed().message("数据修改失败");
        }

    }

    /**
     * 通过主键ID删除单条数据
     *
     * @param adminId 主键ID
     * @return 删除结果
     */
    @PostMapping("/deleteAdminDataById")
    public Result deleteAdminDataById(String adminId) {

        boolean result = adminService.deleteAdminDataById(adminId);

        if (result) {
            return Result.success().message("数据删除成功");
        } else {
            return Result.failed().message("数据删除失败");
        }

    }

}

