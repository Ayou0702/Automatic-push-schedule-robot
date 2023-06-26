package com.enterprise.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleVo {
    // 角色ID
    private String id;

    // 角色名称
    private String roleName;

    // 角色编码
    private String roleCode;

    // 备注
    private String remark;

    // 权限编号集合
    private String[] permissionList;
}
