package com.enterprise.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.enterprise.entity.Role;
import com.enterprise.entity.RolePermission;
import com.enterprise.entity.vo.RoleVo;
import com.enterprise.mapper.RoleMapper;
import com.enterprise.service.RolePermissionService;
import com.enterprise.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@DS("prefersmin_permission")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Resource
    private RolePermissionService rolePermissionService;

    /**
     * 添加角色
     *
     * @param roleVo 包含给予的权限集合数据的角色实体类
     * @return 添加结果
     */
    @Override
    public boolean createRole(RoleVo roleVo) {
        // 添加角色
        Role role = new Role().builder()
                .roleName(roleVo.getRoleName())
                .roleCode(roleVo.getRoleCode())
                .remark(roleVo.getRemark())
                .build();
        int i = this.baseMapper.insert(role);
        // 解析前端传递的权限集合并添加至中间表
        addRolePermission(role.getId(), roleVo.getPermissionList());
        return i >= 1;
    }

    /**
     * 修改角色
     *
     * @param roleVo 包含给予的权限集合数据的角色实体类
     * @return 修改结果
     */
    @Override
    public boolean updateRole(RoleVo roleVo) {
        // 角色信息修改
        Role role = new Role().builder()
                .id(roleVo.getId())
                .roleName(roleVo.getRoleName())
                .roleCode(roleVo.getRoleCode())
                .remark(roleVo.getRemark())
                .build();
        int i = this.baseMapper.updateById(role);
        // 删除中间表数据
        boolean flag = rolePermissionService.remove(new QueryWrapper<RolePermission>().eq("role_id", roleVo.getId()));
        // 重新添加中间表数据
        addRolePermission(role.getId(), roleVo.getPermissionList());
        return i >= 1;
    }

    /**
     * 删除角色
     *
     * @param id 角色ID
     * @return 删除结果
     */
    @Override
    public boolean deleteRole(String id) {
        this.baseMapper.deleteById(id);
        // 删除中间表数据
        rolePermissionService.remove(new QueryWrapper<RolePermission>().eq("role_id", id));
        return true;
    }

    /**
     * 分页查询角色
     *
     * @param current 页码
     * @param size    查询条数
     * @return 已分页的角色列表
     */
    @Override
    public IPage<Role> pageQuery(Integer current, Integer size) {
        // 分页配置对象
        IPage<Role> pageInfo = new Page<Role>().setCurrent(current).setSize(size);
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        // 设置排序防止SqlServer数据库报错
        wrapper.orderByAsc("id");
        pageInfo = this.page(pageInfo, wrapper);
        return pageInfo;
    }

    /**
     * 角色-权限中间表添加数据
     *
     * @param roleId         角色编号
     * @param PermissionList 权限列表
     */
    public void addRolePermission(String roleId, String[] PermissionList) {
        // 创建List集合用于添加数据
        List<RolePermission> rolePermissionList = new ArrayList<>();
        // 遍历菜单数组
        for (String perId : PermissionList) {
            //RolePermission对象
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(perId);
            //封装到list集合
            rolePermissionList.add(rolePermission);
        }
        //添加到角色菜单关系表
        rolePermissionService.saveBatch(rolePermissionList);
    }
}
