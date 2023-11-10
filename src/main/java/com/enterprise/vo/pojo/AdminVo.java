package com.enterprise.vo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminVo {

    // 管理员编号
    private String id;

    // 用户名
    private String userName;

    // 密码
    private String passWord;

    // 昵称
    private String nikeName;

    // 头像
    private String salt;

    // 角色编号
    private String roleId;

    // 角色名称
    private String roleName;


}
