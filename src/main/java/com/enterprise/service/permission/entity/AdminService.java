package com.enterprise.service.permission.entity;


import com.enterprise.vo.pojo.AdminVo;

public interface AdminService {

    // 管理员登录
    AdminVo Login(String userName, String passWord);

}
