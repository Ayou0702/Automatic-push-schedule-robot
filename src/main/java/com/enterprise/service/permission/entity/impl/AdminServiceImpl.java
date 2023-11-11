package com.enterprise.service.permission.entity.impl;

import com.enterprise.mapper.permission.entity.AdminDataMapper;
import com.enterprise.service.permission.entity.AdminService;
import com.enterprise.vo.permission.entity.AdminData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (Admin)表服务实现类
 *
 * @author PrefersMin
 * @since 2023-11-11 20:48:56
 */
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    /**
     * (Admin)表数据库访问层
     */
    private final AdminDataMapper adminDataMapper;

    /**
     * 查询所有数据
     *
     * @return 所有数据
     */
    public List<AdminData> getAllAdmin() {
        return adminDataMapper.getAllAdmin();
    }

    /**
     * 通过主键ID查询单条数据
     *
     * @param adminId 主键ID
     * @return 单条数据
     */
    @Override
    public AdminData queryAdminDataById(String adminId) {
        return adminDataMapper.queryAdminDataById(adminId);
    }

    /**
     * 新增数据
     *
     * @param admin 实体
     * @return 新增结果
     */
    @Override
    public boolean insertAdminData(AdminData admin) {
        return adminDataMapper.insertAdminData(admin) > 0;
    }

    /**
     * 编辑数据
     *
     * @param admin 实体
     * @return 编辑结果
     */
    @Override
    public boolean updateAdminData(AdminData admin) {
        return adminDataMapper.updateAdminData(admin) > 0;
    }

    /**
     * 通过主键ID删除单条数据
     *
     * @param adminId 主键ID
     * @return 删除结果
     */
    @Override
    public boolean deleteAdminDataById(String adminId) {
        return adminDataMapper.deleteAdminDataById(adminId) > 0;
    }

    /**
     * 计次指定AccountID的数据，用于查重
     *
     * @param adminAccountId 用户ID
     *
     * @return 返回满足查询条件的记录数
     */
    @Override
    public int queryAdminDataCountById(String adminAccountId) {
        return adminDataMapper.queryAdminDataCountById(adminAccountId);
    }

    /**
     * 计次指定号码的数据，用于查重
     *
     * @param adminPhoneNumber 用户手机号
     *
     * @return 返回满足查询条件的记录数
     */
    @Override
    public int queryAdminDataCountByPhone(String adminPhoneNumber) {
        return adminDataMapper.queryAdminDataCountByPhone(adminPhoneNumber);
    }

}
