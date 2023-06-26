package com.enterprise.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.enterprise.entity.vo.AdminVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@DS("slave_1")
@Mapper
public interface AdminMapper {

    // 管理员表和角色表连接查询
    @Select("SELECT a.id, a.user_name, a.nike_name, a.salt, r.role_name, r.id as role_id\n" +
            "        FROM l_admin AS a,\n" +
            "             l_role AS r,\n" +
            "             l_admin_role AS ar\n" +
            "        WHERE a.id = ar.admin_id\n" +
            "          AND r.id = ar.role_id\n" +
            "          AND a.id = '${adminId}'")
    AdminVo findAdminAndRoleById(@Param("adminId") String id);

    // 管理员登录
    @Select("SELECT a.id, a.user_name, a.nike_name, a.salt, r.role_name, r.id as role_id\n" +
            "        FROM l_admin AS a,\n" +
            "             l_role AS r,\n" +
            "             l_admin_role AS ar\n" +
            "        WHERE a.id = ar.admin_id\n" +
            "          AND r.id = ar.role_id\n" +
            "          AND a.user_name = '${userName}'\n" +
            "          AND a.pass_word = '${passWord}'")
    AdminVo login(@Param("userName") String userName, @Param("passWord") String passWord);

}
