package com.enterprise.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("l_permission")
public class Permission {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    private String pid;

    private String name;

    private Integer type;

    private Integer orderNum;

    private String permissionValue;

    private String path;

    private String component;

    private String icon;

    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    // 菜单层级
    @TableField(exist = false)
    private Integer level;

    // 子级菜单集合
    @TableField(exist = false)
    private List<Permission> children;

    // 是否选中
    @TableField(exist = false)
    private boolean isSelect;
}
