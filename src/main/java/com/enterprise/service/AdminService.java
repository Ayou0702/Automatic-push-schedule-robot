package com.enterprise.service;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.enterprise.entity.vo.AdminVo;

@DS("prefersmin_permission")
public interface AdminService {
    // 添加管理员
    boolean createAdmin(AdminVo adminVo);

    // 修改管路员
    boolean updateAdmin(AdminVo adminVo);

    // 管理员登录
    AdminVo Login(String userName, String passWord);

    // 管理员表和角色表连接查询
    AdminVo findAdminAndRoleById(String id);
}
