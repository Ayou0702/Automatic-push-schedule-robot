package com.enterprise.controller;

import com.alibaba.fastjson.JSONObject;
import com.enterprise.entity.vo.ResultVo;
import com.enterprise.util.LogUtil;
import com.enterprise.util.Result;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

/**
 * 负责头像数据的Controller
 *
 * @author PrefersMin
 * @version 1.0
 */
@RestController
public class AvatarController {

    final ApplicationHome applicationHome = new ApplicationHome(this.getClass());

    @Resource
    Result result;

    /**
     * 获取头像数据
     *
     * @author PrefersMin
     *
     * @param avatarData 需要获取的头像数据(头像ID和头像类型)
     * @return 返回获取到的头像数据
     */
    @PostMapping("/getAvatar")
    public ResultVo getAvatar(@RequestBody String avatarData) {

        // 反序列化获取到的头像数据
        String avatarType = JSONObject.parseObject(avatarData).getString("avatarType");
        String avatarId = JSONObject.parseObject(avatarData).getString("avatarId");

        // 获取当前程序的执行路径并拼接
        Path imagePath = Paths.get(applicationHome.getDir() + File.separator + avatarType + File.separator + avatarId + ".jpg");

        // 尝试读取头像二进制数据
        byte[] imageBytes;
        try {
            imageBytes = Files.readAllBytes(imagePath);
        } catch (IOException e) {
            String message = "ID为" + avatarId + "的图片加载异常，请检查";
            LogUtil.error(message);
            return result.failed(400, "加载头像失败", message);
        }

        // 转Base64
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);

        return result.success(200, "加载头像成功", "data:image/jpeg;base64," + base64Image);

    }

    /**
     * 上传头像数据
     *
     * @author PrefersMin
     *
     * @param avatarFile 头像数据
     * @param avatarId 头像ID
     * @param avatarType 头像类型
     * @return 返回上传结果
     */
    @PostMapping("/uploadAvatar")
    public ResultVo uploadAvatar(@RequestParam("file") MultipartFile avatarFile, int avatarId, String avatarType) {

        // 非空判断
        if (avatarFile.isEmpty()) {
            return result.failed(400,"上传头像失败","头像数据为空");
        }

        // 固定jpg后缀
        String filename = avatarId + ".jpg";

        // 获取当前程序的执行路径并拼接
        String pre = applicationHome.getDir() + File.separator + avatarType + File.separator;
        String path = pre + filename;

        LogUtil.info(path);

        // 尝试写入头像数据
        try {
            avatarFile.transferTo(new File(path));
            return result.success(200,"上传头像成功","类型为" + avatarType + "、ID为" + avatarId + "的头像上传成功");
        } catch (IOException e) {
            return result.failed(400,"上传头像失败",e.getMessage());
        }

    }

}
