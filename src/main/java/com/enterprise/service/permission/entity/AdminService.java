package com.enterprise.service.permission.entity;

import com.enterprise.vo.permission.entity.AdminData;

import java.util.List;

/**
 * (Admin)表服务接口
 *
 * @author PrefersMin
 * @since 2023-11-11 20:48:56
 */
public interface AdminService {

    /**
     * 查询所有数据
     *
     * @return 所有数据
     */
    List<AdminData> getAllAdmin();

    /**
     * 通过主键ID查询单条数据
     *
     * @param adminId 主键ID
     * @return 单条数据
     */
    AdminData queryAdminDataById(String adminId);

    /**
     * 新增数据
     *
     * @param admin 实体
     * @return 新增结果
     */
    boolean insertAdminData(AdminData admin);

    /**
     * 编辑数据
     *
     * @param admin 实体
     * @return 编辑结果
     */
    boolean updateAdminData(AdminData admin);

    /**
     * 通过主键ID删除单条数据
     *
     * @param adminId 主键ID
     * @return 删除结果
     */
    boolean deleteAdminDataById(String adminId);

    /**
     * 计次指定AccountID的数据，用于查重
     *
     * @param adminAccountId 用户ID
     * @return 返回满足查询条件的记录数
     */
    int queryAdminDataCountById(String adminAccountId);

    /**
     * 计次指定号码的数据，用于查重
     *
     * @param adminPhoneNumber 用户手机号
     * @return 返回满足查询条件的记录数
     */
    int queryAdminDataCountByPhone(String adminPhoneNumber);

}
