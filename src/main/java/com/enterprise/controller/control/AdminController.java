package com.enterprise.controller.control;

import com.enterprise.common.handler.Result;
import com.enterprise.entity.vo.AdminVo;
import com.enterprise.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 负责Admin表数据的Controller
 *
 * @author PrefersMin
 * @version 1.0
 */
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    /**
     * 添加管理员
     *
     * @param adminVo 包含角色信息的管理员实体类
     * @return 统一接口返回值
     */
    @PostMapping("/create")
    public Result createAdmin(@RequestBody AdminVo adminVo) {
        boolean flag = adminService.createAdmin(adminVo);
        if (flag) {
            return Result.success().message("添加成功");
        } else {
            return Result.failed().message("添加失败");
        }
    }

    /**
     * 修改管理员
     *
     * @param adminVo 包含角色信息的管理员实体类
     * @return 统一接口返回值
     */
    @PostMapping("/update")
    public Result updateAdmin(@RequestBody AdminVo adminVo) {
        boolean flag = adminService.updateAdmin(adminVo);
        if (flag) {
            return Result.success().message("修改成功");
        } else {
            return Result.failed().message("修改失败");
        }
    }

    /**
     * 删除管理员
     *
     * @param id 管理员编号
     * @return 统一接口返回值
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteAdmin(@PathVariable String id) {
        // adminService.removeById(id);
        return Result.success().message("删除成功");
    }

    /**
     * 根据管理员编号查询包含角色信息的管理员信息
     *
     * @param id 管理员编号
     * @return 统一接口返回值
     */
    @GetMapping("/{id}")
    public Result findAdminAndRole(@PathVariable String id) {
        AdminVo adminVo = adminService.findAdminAndRoleById(id);
        return Result.success().data("adminVo", adminVo);
    }

}
