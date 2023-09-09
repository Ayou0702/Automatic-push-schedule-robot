package com.enterprise.controller.control;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.enterprise.entity.Admin;
import com.enterprise.entity.vo.AdminVo;
import com.enterprise.entity.vo.ResultVo;
import com.enterprise.service.AdminService;
import com.enterprise.util.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    /**
     * 封装返回结果
     */
    private final Result result;

    private final AdminService adminService;

    /**
     * 添加管理员
     *
     * @param adminVo 包含角色信息的管理员实体类
     * @return 统一接口返回值
     */
    @PostMapping("/create")
    public ResultVo createAdmin(@RequestBody AdminVo adminVo) {
        boolean flag = adminService.createAdmin(adminVo);
        if (flag) {
            return result.success("添加成功");
        } else {
            return result.failed("添加失败");
        }
    }

    /**
     * 修改管理员
     *
     * @param adminVo 包含角色信息的管理员实体类
     * @return 统一接口返回值
     */
    @PostMapping("/update")
    public ResultVo updateAdmin(@RequestBody AdminVo adminVo) {
        boolean flag = adminService.updateAdmin(adminVo);
        if (flag) {
            return result.success("修改成功");
        } else {
            return result.failed("修改失败");
        }
    }

    /**
     * 删除管理员
     *
     * @param id 管理员编号
     * @return 统一接口返回值
     */
    @DeleteMapping("/delete/{id}")
    public ResultVo deleteAdmin(@PathVariable String id) {
        // adminService.removeById(id);
        return result.success("删除成功");
    }

    /**
     * 根据管理员编号查询包含角色信息的管理员信息
     *
     * @param id 管理员编号
     * @return 统一接口返回值
     */
    @GetMapping("/{id}")
    public ResultVo findAdminAndRole(@PathVariable String id) {
        AdminVo adminVo = adminService.findAdminAndRoleById(id);
        return result.success("adminVo", adminVo);
    }

}
