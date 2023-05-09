package com.enterprise.mapper;

import com.enterprise.entity.TeacherData;
import com.enterprise.entity.UserData;
import org.apache.ibatis.annotations.*;

import java.io.ByteArrayInputStream;
import java.util.List;

@Mapper
public interface UserDataMapper {

    @Select("SELECT * FROM user_data")
    List<UserData> queryAllUserData();

    @Select("SELECT * FROM user_data WHERE user_id=#{userId}")
    UserData queryUserDataByUserId(int userId);

    @Select("SELECT user_avatar FROM user_data WHERE user_id=#{userId}")
    UserData queryUserAvatarByUserId(int userId);

    @Update("UPDATE user_data SET user_name = #{userName}, user_email = #{userEmail} WHERE user_id=#{userId}")
    boolean updateUserData(UserData userData);

    @Update("UPDATE user_data SET user_avatar = #{userAvatar} WHERE user_id = #{userId}")
    boolean modifyUserAvatar(ByteArrayInputStream userAvatar, int userId);

    @Delete("DELETE FROM user_data WHERE user_id=#{userId}")
    boolean deleteUserData(int userId);

    @Insert("INSERT INTO user_data(user_name,user_email) VALUES (#{userName},#{userEmail})")
    boolean addUserData(UserData userData);

}
