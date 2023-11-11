package com.enterprise.vo.permission.entity;

import lombok.Data;

import java.util.Date;

/**
 * (Admin)表实体类
 *
 * @author PrefersMin
 * @since 2023-11-11 20:48:56
 */
@Data
public class AdminData {

    /**
     * 用户ID
     */
    private String adminId;

    /**
     * 用户账号
     */
    private String adminAccountId;

    /**
     * 用户密码
     */
    private String adminAccountPassword;

    /**
     * 用户名称
     */
    private String adminNickName;

    /**
     * 用户手机号码
     */
    private String adminPhoneNumber;

    /**
     * 用户创建时间
     */
    private Date adminCreateTime;

}

