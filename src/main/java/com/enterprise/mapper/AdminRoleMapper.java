package com.enterprise.mapper;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.enterprise.entity.AdminRole;
import org.apache.ibatis.annotations.Mapper;

@DS("slave_1")
@Mapper
public interface AdminRoleMapper extends BaseMapper<AdminRole> {
}
