package com.enterprise.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.enterprise.entity.Admin;
import com.enterprise.entity.AdminRole;
import com.enterprise.entity.vo.AdminVo;
import com.enterprise.mapper.AdminMapper;
import com.enterprise.service.AdminRoleService;
import com.enterprise.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
@DS("prefersmin_permission")
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRoleService adminRoleService;

    private final AdminMapper adminMapper;

    /**
     * 添加管理员
     *
     * @param adminVo 包含有角色信息的管理员实体类
     * @return 添加结果
     */
    @Override
    public boolean createAdmin(AdminVo adminVo) {
        // Admin admin = new Admin().builder()
        //         .userName(adminVo.getUserName())
        //         .nikeName(adminVo.getNikeName())
        //         .passWord(adminVo.getPassWord())
        //         .salt(adminVo.getSalt())
        //         .isDelete(0)
        //         .build();
        // int i = adminMapper.insert(admin);
        // // 管理员-角色中间表添加数据
        // AdminRole adminRole = new AdminRole().builder()
        //         .adminId(admin.getId())
        //         .roleId(adminVo.getRoleId())
        //         .build();
        // adminRoleService.save(adminRole);
        // return i >= 1;
        return true;
    }

    /**
     * 修改管理员
     *
     * @param adminVo 包含有角色信息的管理员实体类
     * @return 修改结果
     */
    @Override
    public boolean updateAdmin(AdminVo adminVo) {
        // Admin admin = Admin.builder()
        //         .id(adminVo.getId())
        //         .userName(adminVo.getUserName())
        //         .nikeName(adminVo.getNikeName())
        //         .passWord(adminVo.getPassWord())
        //         .salt(adminVo.getSalt())
        //         .build();
        // int i = adminMapper.updateById(admin);
        // // 管理员-角色中间表修改数据
        // // 查询是否有变动
        // AdminRole byAdminId = adminRoleService.getByAdminId(admin.getId());
        // if (byAdminId.getRoleId() != adminVo.getRoleId()) {
        //     byAdminId.setRoleId(adminVo.getRoleId());
        //     adminRoleService.updateAdminRole(byAdminId);
        // }
        // return i >= 1;
        return true;
    }

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

    /**
     * 根据管理员编号连接查询角色表
     *
     * @param id 管理员编号
     * @return 包含角色信息的管理员实体类
     */
    @Override
    public AdminVo findAdminAndRoleById(String id) {
        AdminVo adminVo = adminMapper.findAdminAndRoleById(id);
        return adminVo;
    }
}
