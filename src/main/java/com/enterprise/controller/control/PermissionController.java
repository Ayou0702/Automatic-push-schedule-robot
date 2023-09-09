package com.enterprise.controller.control;

import com.enterprise.common.handler.Result;
import com.enterprise.entity.Permission;
import com.enterprise.service.PermissionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Resource
    private PermissionService permissionService;

    /**
     * 添加菜单
     *
     * @param permission 菜单实体类
     * @return 统一接口返回值
     */
    @PostMapping("/create")
    public Result createPermission(@RequestBody Permission permission) {
        boolean flag = permissionService.createPermission(permission);
        if (flag) {
            return Result.OK().message("添加成功");
        } else {
            return Result.Error().message("添加失败");
        }
    }

    /**
     * 修改菜单
     *
     * @param permission 菜单实体类
     * @return 统一接口返回值
     */
    @PostMapping("/update")
    public Result updatePermission(@RequestBody Permission permission) {
        boolean flag = permissionService.updatePermission(permission);
        if (flag) {
            return Result.OK().message("修改成功");
        } else {
            return Result.Error().message("修改失败");
        }
    }

    /**
     * 删除菜单及其所有子节点
     *
     * @param id 菜单编号
     * @return 统一接口返回值
     */
    @DeleteMapping("/delete/{id}")
    public Result deletePermission(@PathVariable String id) {
        permissionService.removeChildById(id);
        return Result.OK();
    }

    /**
     * 根据ID查询单个菜单
     *
     * @param id 菜单编号
     * @return 统一接口返回值
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable String id) {
        Permission permission = permissionService.getById(id);
        return Result.OK().data("permission", permission);
    }

    /**
     * 获取可以为父节点的一级菜单，提供给添加和修改时的父节点下拉框
     *
     * @return 统一接口返回值
     */
    @GetMapping("/parentNodes")
    public Result getParentNodes() {
        List<Permission> parentList = permissionService.getParentNodes();
        return Result.OK().data("parentList", parentList);
    }

    /**
     * 获取父子节点分级完毕的菜单列表
     *
     * @return 统一接口返回值
     */
    @GetMapping("/all")
    public Result getAllNode() {
        List<Permission> permissionList = permissionService.queryAllMenu();
        return Result.OK().data("Menu", permissionList);
    }

}
