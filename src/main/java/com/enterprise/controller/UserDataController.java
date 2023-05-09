package com.enterprise.controller;

import com.alibaba.fastjson2.JSONObject;
import com.enterprise.entity.TeacherData;
import com.enterprise.entity.UserData;
import com.enterprise.entity.vo.ResultVo;
import com.enterprise.service.UserDataService;
import com.enterprise.util.LogUtil;
import com.enterprise.util.Result;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@RestController
public class UserDataController {
    
    @Resource
    UserDataService userDataService;

    @Resource
    Result result;

    @Resource
    PlatformTransactionManager platformTransactionManager;

    List<Integer> deleteUserData;
    List<UserData> diffUser;
    List<UserData> addUserData;

    @GetMapping("/getUserData")
    public ResultVo getUserData() {

        List<UserData> userDataList = userDataService.queryAllUserData();

        if (userDataList.isEmpty()) {
            return result.failed("用户数据加载失败");
        }

        return result.success("用户数据加载成功", userDataList);

    }

    @PostMapping("/modifyUserData")
    public ResultVo modifyUserData(@RequestBody String userData) {

        // 开始事务
        TransactionStatus transaction = platformTransactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            deleteUserData = JSONObject.parseObject(userData).getJSONArray("deleteUserData").toList(Integer.class);
            diffUser = JSONObject.parseObject(userData).getJSONArray("diffUser").toJavaList(UserData.class);
            addUserData = JSONObject.parseObject(userData).getJSONArray("addUserData").toJavaList(UserData.class);
        } catch (Exception e) {
            return result.failed(e.getMessage());
        }

        try {

            if (!deleteUserData.isEmpty()) {
                deleteUserData(deleteUserData);
            }

            if (!diffUser.isEmpty()) {
                updateUserData(diffUser);
            }

            if (!addUserData.isEmpty()) {
                addUserData(addUserData);
            }

            platformTransactionManager.commit(transaction);
        } catch (Exception e) {
            platformTransactionManager.rollback(transaction);
            return result.failed(e.getMessage());
        }
        return result.success("用户数据修改成功");

    }

    public void updateUserData(List<UserData> userDataList) {
        List<String> record = new ArrayList<>();
        try {
            for (UserData userData : userDataList) {
                boolean updateResult = userDataService.updateUserData(userData);
                if (!updateResult) {
                    LogUtil.error("ID为" + userData.getUserId() + "的用户信息修改失败");
                    throw new Exception("修改用户信息失败,操作已回滚");
                } else {
                    record.add("用户信息被修改，用户信息：" + userData);
                }
            }
            record.forEach(LogUtil::info);
            LogUtil.info(userDataList.size() + "条用户数据被修改");
        } catch (Exception e) {
            LogUtil.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }


    public void deleteUserData(List<Integer> courseIdList) {
        List<String> record = new ArrayList<>();
        try {
            for (int courseId : courseIdList) {
                boolean deleteResult = userDataService.deleteUserData(courseId);
                if (!deleteResult) {
                    LogUtil.error("ID为" + courseId + "的用户信息删除失败");
                    throw new Exception("删除用户信息失败,操作已回滚");
                } else {
                    record.add("用户信息被删除，用户信息：" + courseId);
                }
            }
            record.forEach(LogUtil::info);
            LogUtil.info(courseIdList.size() + "条用户数据被删除");
        } catch (Exception e) {
            LogUtil.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public void addUserData(List<UserData> userDataList) {
        List<String> record = new ArrayList<>();
        try {
            for (UserData userData : userDataList) {
                boolean addResult = userDataService.addUserData(userData);
                if (!addResult) {
                    LogUtil.error("新增用户信息失败，用户信息：" + userData);
                    throw new Exception("新增用户信息失败,操作已回滚");
                } else {
                    record.add("新增用户信息，用户信息：" + userData);
                }
            }
            record.forEach(LogUtil::info);
            LogUtil.info("新增" + userDataList.size() + "条用户信息");
        } catch (Exception e) {
            LogUtil.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @PostMapping("/modifyUserAvatar")
    public ResultVo modifyUserAvatar(@RequestParam("file") MultipartFile file, @RequestParam("userId") int userId) {

        if (file.isEmpty()) {
            return result.failed("文件上传失败");
        }

        String fileName = file.getOriginalFilename();
        String extension = StringUtils.getFilenameExtension(fileName); // 获取文件扩展名

        if (extension == null) {
            return result.failed("文件不是图片");
        }

        if (!extension.equalsIgnoreCase("png") && !extension.equalsIgnoreCase("jpg") && !extension.equalsIgnoreCase("jpeg")) {
            return result.failed("文件不是图片");
        }

        try {

            byte[] bytes = file.getBytes();

            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);

            boolean modifyResult = userDataService.modifyUserAvatar(inputStream,userId);

            if (!modifyResult) {
                LogUtil.error("ID为" + userId + "的用户头像修改失败");
                throw new Exception("修改用户头像失败,操作已回滚");
            } else {
                LogUtil.info("用户头像被修改，教师ID：" + userId);
            }

            return result.success("用户头像上传成功",userId);

        } catch (Exception e) {
            LogUtil.error(e.getMessage());
            return result.failed("用户头像上传失败");
        }

    }

    @PostMapping("/queryUserAvatarByUserId")
    public ResultVo queryUserAvatarByUserId(@RequestBody String userId) {

        userId = JSONObject.parseObject(userId).getString("userId");

        UserData userData = userDataService.queryUserAvatarByUserId(Integer.parseInt(userId));

        if (isNull(userData)) {
            return result.failed("用户头像加载失败");
        }

        return result.success("用户头像加载成功", userData);

    }

    @PostMapping("/queryUserDataByUserId")
    public ResultVo queryUserDataByUserId(@RequestBody String userId) {

        userId = JSONObject.parseObject(userId).getString("userId");

        UserData userData = userDataService.queryUserAvatarByUserId(Integer.parseInt(userId));

        if (isNull(userData)) {
            return result.failed("用户数据加载失败");
        }

        return result.success("用户数据加载成功", userData);

    }
    
}
