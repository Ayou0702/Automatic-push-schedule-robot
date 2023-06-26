package com.enterprise.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.enterprise.entity.Permission;
import com.enterprise.entity.Role;
import com.enterprise.entity.vo.ResultVo;
import com.enterprise.entity.vo.RoleVo;
import com.enterprise.service.PermissionService;
import com.enterprise.service.RoleService;
import com.enterprise.util.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

    /**
     * 封装返回结果
     */
    private final Result result;
    private final RoleService roleService;

    private final PermissionService permissionService;

    /**
     * 添加角色
     *
     * @param roleVo 包含选中的权限集合的角色实体类
     * @return 统一接口返回值
     */
    @PostMapping("/create")
    public ResultVo createRole(@RequestBody RoleVo roleVo) {
        boolean flag = roleService.createRole(roleVo);
        if (flag) {
            return result.success("添加成功");
        } else {
            return result.failed("添加失败");
        }
    }

    /**
     * 修改角色
     *
     * @param roleVo 包含选中权限集合的角色实体类
     * @return 统一接口返回值
     */
    @PostMapping("/update")
    public ResultVo updateRole(@RequestBody RoleVo roleVo) {
        boolean flag = roleService.updateRole(roleVo);
        if (flag) {
            return result.success("修改成功");
        } else {
            return result.failed("修改失败");
        }
    }

    /**
     * 删除角色
     *
     * @param id 角色ID
     * @return 统一接口返回值
     */
    @DeleteMapping("/delete/{id}")
    public ResultVo deleteRole(@PathVariable String id) {
        boolean flag = roleService.deleteRole(id);
        if (flag) {
            return result.success("删除成功");
        } else {
            return result.failed("删除失败");
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
    public ResultVo pageQuery(
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "15") Integer pageSize) {
        IPage<Role> page = roleService.pageQuery(pageNo, pageSize);
        return result.success("pageList", page);
    }


    /**
     * 查询所有角色
     *
     * @return 统一接口返回值
     */
    @GetMapping("/list")
    public ResultVo findList() {
        return result.success("roleList", roleService.list());
    }

    /**
     * 根据角色ID获取其所拥有的权限
     *
     * @param id 角色ID
     * @return 统一接口返回值
     */
    @GetMapping("/query/permission/{id}")
    public ResultVo queryPermission(@PathVariable String id) {
        // 获取该角色绑定的菜单项
        List<Permission> permissionList = permissionService.findMenuByRoleId(id, true);
        return result.success("permissionList", permissionList);
    }
}
