package com.enterprise.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("l_admin")
public class Admin {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    private String userName;

    private String passWord;

    private String nikeName;

    private String salt;

    @TableLogic
    private Integer isDelete;

    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;
}
