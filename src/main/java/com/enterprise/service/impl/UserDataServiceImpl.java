package com.enterprise.service.impl;

import com.enterprise.entity.UserData;
import com.enterprise.mapper.UserDataMapper;
import com.enterprise.service.UserDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.util.List;

@Service
public class UserDataServiceImpl implements UserDataService {

    @Resource
    UserDataMapper userDataMapper;

    @Override
    public List<UserData> queryAllUserData() {
        return userDataMapper.queryAllUserData();
    }

    @Override
    public UserData queryUserDataByUserId(int userId) {
        return userDataMapper.queryUserDataByUserId(userId);
    }

    @Override
    public UserData queryUserAvatarByUserId(int userId) {
        return userDataMapper.queryUserAvatarByUserId(userId);
    }

    @Override
    public boolean updateUserData(UserData userData) {
        return userDataMapper.updateUserData(userData);
    }

    @Override
    public boolean modifyUserAvatar(ByteArrayInputStream userAvatar, int userId) {
        return userDataMapper.modifyUserAvatar(userAvatar, userId);
    }

    @Override
    public boolean deleteUserData(int userId) {
        return userDataMapper.deleteUserData(userId);
    }

    @Override
    public boolean addUserData(UserData userData) {
        return userDataMapper.addUserData(userData);
    }

}
