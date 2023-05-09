package com.enterprise.service;

import com.enterprise.entity.UserData;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface UserDataService {

    List<UserData> queryAllUserData();

    UserData queryUserDataByUserId(int userId);

    UserData queryUserAvatarByUserId(int userId);

    boolean updateUserData(UserData userData);

    boolean modifyUserAvatar(ByteArrayInputStream userAvatar, int userId);

    boolean deleteUserData(int userId);

    boolean addUserData(UserData userData);

}
