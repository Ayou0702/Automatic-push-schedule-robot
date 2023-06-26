package com.enterprise.service.impl;

import cn.dev33.satoken.stp.StpInterface;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.enterprise.service.PermissionService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
@DS("prefersmin_permission")
public class StpInterfaceImpl implements StpInterface {
    @Resource
    private PermissionService permissionService;

    /**
     * 根据管理员ID获取权限集合
     *
     * @param adminId
     * @param s
     * @return 权限列表
     */
    @Override
    public List<String> getPermissionList(Object adminId, String s) {
        List<String> valueList = permissionService.findValueByAdminId((String) adminId);
        return valueList;
    }


    @Override
    public List<String> getRoleList(Object o, String s) {
        return null;
    }
}
