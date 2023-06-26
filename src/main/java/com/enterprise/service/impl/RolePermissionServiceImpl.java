package com.enterprise.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.enterprise.entity.RolePermission;
import com.enterprise.mapper.RolePermissionMapper;
import com.enterprise.service.RolePermissionService;
import org.springframework.stereotype.Service;

@Service
@DS("prefersmin_permission")
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {
}
