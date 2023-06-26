package com.enterprise.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.enterprise.entity.Permission;

import java.util.List;

public interface PermissionService extends IService<Permission> {
    //获取全部菜单--分级
    List<Permission> queryAllMenu();

    // 获取适合作为父节点的菜单
    List<Permission> getParentNodes();

    // 根据角色获取菜单
    List<Permission> findMenuByRoleId(String roleId, boolean flag);

    // 根据管理员ID获取其角色和权限字符
    List<String> findValueByAdminId(String adminId);

    // 添加菜单
    boolean createPermission(Permission permission);

    // 修改菜单
    boolean updatePermission(Permission permission);

    // 递归删除菜单
    void removeChildById(String id);
}
