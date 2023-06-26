package com.enterprise.mapper;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.enterprise.entity.RolePermission;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@DS("slave_1")
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
}
