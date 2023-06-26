package com.enterprise.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.enterprise.entity.AdminRole;
import com.enterprise.mapper.AdminRoleMapper;
import com.enterprise.service.AdminRoleService;
import org.springframework.stereotype.Service;

@Service
@DS("prefersmin_permission")
public class AdminRoleServiceImpl extends ServiceImpl<AdminRoleMapper, AdminRole> implements AdminRoleService {
    /**
     * 管理员-角色中间表添加数据
     *
     * @param adminRole 管理员-角色中间表添加实体类
     * @return 添加结果
     */
    @Override
    public boolean createAdminRole(AdminRole adminRole) {
        int i = this.baseMapper.insert(adminRole);
        if (i >= 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 管理员-角色中间表修改
     *
     * @param adminRole 管理员-角色中间表添加实体类
     * @return 修改结果
     */
    @Override
    public boolean updateAdminRole(AdminRole adminRole) {
        int i = this.baseMapper.updateById(adminRole);
        if (i >= 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据管理员ID查询数据
     *
     * @param id 管理员ID
     * @return 中间表数据
     */
    @Override
    public AdminRole getByAdminId(String id) {
        QueryWrapper<AdminRole> wrapper = new QueryWrapper<>();
        wrapper.eq("admin_id", id);
        AdminRole adminRole = this.baseMapper.selectOne(wrapper);
        return adminRole;
    }
}
