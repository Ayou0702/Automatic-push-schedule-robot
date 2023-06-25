package com.enterprise.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.enterprise.entity.vo.ResultVo;
import com.enterprise.util.HttpUtil;
import com.enterprise.util.LogUtil;
import com.enterprise.util.Result;
import com.enterprise.util.enums.CloudPhotoType;
import lombok.RequiredArgsConstructor;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 负责图库解析的Controller
 *
 * @author PrefersMin
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
public class PhotoUtilController {

    /**
     * 封装返回结果
     */
    private final Result result;

    /**
     * http工具类
     */
    private final HttpUtil httpUtil;

    /**
     * 获取云图库的所有图片
     *
     * @author PrefersMin
     *
     * @param code 作者代码
     * @return 返回分组图片url
     * @throws IOException IO异常
     */
    @PostMapping("/getCloudPhoto")
    public ResultVo CloudPhotoUtilController(@RequestParam String code) throws IOException {

        // V3固定参数
        String urlV3 = "https://dycloud.onlineweixin.com/wallpaper/selectShareUserIdByPopuWordV3.action";
        String dataV3 = "popuWord=" + code;

        // 需要带上该头部信息，否则提示未登录
        Header header = new BasicHeader("Referer", "https://tmaservice.developer.toutiao.com/?appid=tt89acff111696cb21&version=1.0.166");

        // V3结果
        JSONObject resultV3 = JSONObject.parseObject(httpUtil.postUrl(urlV3, dataV3, header).getData().toString()).getJSONObject("data");

        // 从V3结果中获取V4参数
        String classId = resultV3.getString("classId");
        String popularizeId = resultV3.getString("popularizeId");
        String accId = resultV3.getString("accId");
        String queryType = resultV3.getString("queryType");

        // V4固定参数
        String urlV4 = "https://dycloud.onlineweixin.com/wallpaper/selectEmoWallpaperHomePageV4.action";
        String dataV4 = "page=0" + "&limit=0" + "&classId=" + classId + "&popularizeId=" + popularizeId + "&shareUserId=&queryType=" + queryType + "&accId=" + accId + "&type=0";

        LogUtil.info("总长度：" + JSONObject.parseObject(httpUtil.postUrl(urlV4, dataV4, header).getData().toString()).getString("total"));

        // 所有图片列表
        JSONArray dataList = JSONObject.parseObject(httpUtil.postUrl(urlV4, dataV4, header).getData().toString()).getJSONArray("data");

        // 声明一个map存放分类后的图片url
        Map<String, List<String>> map = new HashMap<>();

        // 使用枚举类型初始化Map
        for (CloudPhotoType type : CloudPhotoType.values()) {
            map.put(type.getTypeName(), new ArrayList<>());
        }

        // 循环解析图片列表并分类
        for (int i = 0; i < dataList.size(); i++) {
            JSONArray data = dataList.getJSONArray(i);
            for (int j = 0; j < data.size(); j++) {

                // 获取单个图片信息
                JSONObject jsonObject = data.getJSONObject(j);

                // 获取分类
                int id = jsonObject.getInteger("classId");
                String name = CloudPhotoType.getTypeNameByTypeId(id);

                // 截取url
                String dynamicUrl = jsonObject.getString("dynamicUrl");
                int index = dynamicUrl.indexOf("?");
                if (index > -1) {
                    dynamicUrl = dynamicUrl.substring(0, index);
                }

                // 存入map
                List<String> list = map.getOrDefault(name, new ArrayList<>());
                list.add(dynamicUrl);
                map.put(name, list);

            }
        }

        // 获取Map中的所有键值对，检查是否丢图
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            String typeName = entry.getKey();
            List<String> list = entry.getValue();
            LogUtil.info(typeName + "：" + list.size());
        }

        // 返回map
        return result.success(map);

    }

}
