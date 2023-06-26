package com.enterprise.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.enterprise.entity.Role;
import com.enterprise.entity.vo.RoleVo;

public interface RoleService extends IService<Role> {
    // 添加角色
    boolean createRole(RoleVo role);

    // 修改角色
    boolean updateRole(RoleVo role);

    // 删除角色
    boolean deleteRole(String id);

    // 分页查询
    IPage<Role> pageQuery(Integer current, Integer size);
}
