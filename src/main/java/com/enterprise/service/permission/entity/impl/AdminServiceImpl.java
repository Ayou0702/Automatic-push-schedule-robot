package com.enterprise.service.permission.entity.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.enterprise.mapper.permission.entity.AdminMapper;
import com.enterprise.service.permission.entity.AdminService;
import com.enterprise.vo.permission.entity.Admin;
import com.enterprise.vo.pojo.AdminVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@DS("prefersmin_permission")
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminMapper adminMapper;

    /**
     * 管理员登录
     *
     * @param userName 用户名
     * @param passWord 登录密码
     * @return 管理员实体类对象
     */
    @Override
    public AdminVo Login(String userName, String passWord) {
        System.out.println(userName);
        // 构建查询条件
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        Map<String, String> map = new HashMap<>();
        map.put("user_name", userName);
        map.put("pass_word", passWord);
        wrapper.allEq(map);
        // 调用查询方法
        AdminVo adminVo = adminMapper.login(userName, passWord);
        if (adminVo != null) {
            // 清空密码
            adminVo.setPassWord("");
            return adminVo;
        }
        return null;
    }

}
