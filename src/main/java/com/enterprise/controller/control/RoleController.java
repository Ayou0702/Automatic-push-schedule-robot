package com.enterprise.controller.control;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.enterprise.common.handler.Result;
import com.enterprise.entity.Permission;
import com.enterprise.entity.Role;
import com.enterprise.entity.vo.RoleVo;
import com.enterprise.service.PermissionService;
import com.enterprise.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 负责Role表数据的Controller
 *
 * @author PrefersMin
 * @version 1.0
 */
@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    private final PermissionService permissionService;

    /**
     * 添加角色
     *
     * @param roleVo 包含选中的权限集合的角色实体类
     * @return 统一接口返回值
     */
    @PostMapping("/create")
    public Result createRole(@RequestBody RoleVo roleVo) {
        boolean flag = roleService.createRole(roleVo);
        if (flag) {
            return Result.success().message("添加成功");
        } else {
            return Result.failed().message("添加失败");
        }
    }

    /**
     * 修改角色
     *
     * @param roleVo 包含选中权限集合的角色实体类
     * @return 统一接口返回值
     */
    @PostMapping("/update")
    public Result updateRole(@RequestBody RoleVo roleVo) {
        boolean flag = roleService.updateRole(roleVo);
        if (flag) {
            return Result.success().message("修改成功");
        } else {
            return Result.failed().message("修改失败");
        }
    }

    /**
     * 删除角色
     *
     * @param id 角色ID
     * @return 统一接口返回值
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteRole(@PathVariable String id) {
        boolean flag = roleService.deleteRole(id);
        if (flag) {
            return Result.success().message("删除成功");
        } else {
            return Result.failed().message("删除失败");
        }
    }

    /**
     * 分页查询角色
     *
     * @param pageNo   查询页码
     * @param pageSize 查询条数
     * @return 统一接口返回值
     */
    @GetMapping("/page")
    public Result pageQuery(
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "15") Integer pageSize) {
        IPage<Role> page = roleService.pageQuery(pageNo, pageSize);
        return Result.success().data("pageList", page);
    }


    /**
     * 查询所有角色
     *
     * @return 统一接口返回值
     */
    @GetMapping("/list")
    public Result findList() {
        return Result.success().data("roleList", roleService.list());
    }

    /**
     * 根据角色ID获取其所拥有的权限
     *
     * @param id 角色ID
     * @return 统一接口返回值
     */
    @GetMapping("/query/permission/{id}")
    public Result queryPermission(@PathVariable String id) {
        // 获取该角色绑定的菜单项
        List<Permission> permissionList = permissionService.findMenuByRoleId(id, true);
        return Result.success().data("permissionList", permissionList);
    }
}
