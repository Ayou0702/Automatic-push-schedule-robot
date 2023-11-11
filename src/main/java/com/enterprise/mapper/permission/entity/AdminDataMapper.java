package com.enterprise.mapper.permission.entity;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.enterprise.vo.permission.entity.AdminData;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * (Admin)表数据库访问层
 *
 * @author PrefersMin
 * @since 2023-11-11 20:48:56
 */
@Mapper
@DS("permission")
public interface AdminDataMapper {

    /**
     * 查询所有数据
     *
     * @return 所有数据
     */
    @Select("SELECT * FROM admin")
    List<AdminData> getAllAdmin();

    /**
     * 通过主键ID查询单条数据
     *
     * @param adminId 主键ID
     * @return 单条数据
     */
    @Select("SELECT * FROM admin WHERE admin_id = #{adminId}")
    AdminData queryAdminDataById(String adminId);

    /**
     * 新增数据
     *
     * @param admin 实体
     * @return 受影响的行数
     */
    @Insert("INSERT INTO admin(admin_id,admin_account_id,admin_account_password,admin_nick_name,admin_phone_number,admin_create_time) VALUES (#{adminId},#{adminAccountId},#{adminAccountPassword},#{adminNickName},#{adminPhoneNumber},#{adminCreateTime})")
    int insertAdminData(AdminData admin);

    /**
     * 编辑数据
     *
     * @param admin 实体
     * @return 受影响的行数
     */
    @Update("UPDATE admin SET admin_account_id = #{adminAccountId},admin_account_password = #{adminAccountPassword},admin_nick_name = #{adminNickName},admin_phone_number = #{adminPhoneNumber},admin_create_time = #{adminCreateTime} WHERE admin_id = #{adminId}")
    int updateAdminData(AdminData admin);

    /**
     * 通过主键ID删除单条数据
     *
     * @param adminId 主键ID
     * @return 受影响的行数
     */
    @Delete("DELETE FROM admin WHERE admin_id = #{adminId}")
    int deleteAdminDataById(String adminId);

    /**
     * 计次指定AccountID的数据，用于查重
     *
     * @param adminAccountId 用户ID
     * @return 返回满足查询条件的记录数
     */
    @Select("SELECT COUNT(*) FROM admin WHERE admin_account_id = #{adminAccountId}")
    int queryAdminDataCountById(String adminAccountId);

    /**
     * 计次指定号码的数据，用于查重
     *
     * @param adminPhoneNumber 用户手机号
     * @return 返回满足查询条件的记录数
     */
    @Select("SELECT COUNT(*) FROM admin WHERE admin_phone_number = #{adminPhoneNumber}")
    int queryAdminDataCountByPhone(String adminPhoneNumber);

}

