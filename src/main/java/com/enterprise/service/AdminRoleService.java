package com.enterprise.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.enterprise.entity.AdminRole;

public interface AdminRoleService extends IService<AdminRole> {
    // 管理员-角色中间表添加
    boolean createAdminRole(AdminRole adminRole);

    // 管理源-角色中间表修改
    boolean updateAdminRole(AdminRole adminRole);

    // 根据管理员ID获取数据
    AdminRole getByAdminId(String id);


}
