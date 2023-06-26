package com.enterprise.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.enterprise.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
@DS("slave_1")
public interface RoleMapper extends BaseMapper<Role> {
    // 分页获取角色列表
    Page<Role> pageQuery(@Param("page") Page<Role> page);
}
