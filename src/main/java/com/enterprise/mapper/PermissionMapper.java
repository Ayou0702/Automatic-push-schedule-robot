package com.enterprise.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.enterprise.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@DS("slave_1")
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
    // 根据角色获取对应菜单
    List<Permission> findMenuByRole(@Param("roleId") String roleId);

    // 根据管理员ID获取其角色和权限字符
    List<String> findValueByAdminId(@Param("adminId") String adminId);
}
