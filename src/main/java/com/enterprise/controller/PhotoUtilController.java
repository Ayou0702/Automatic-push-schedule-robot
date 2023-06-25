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

@RestController
@RequiredArgsConstructor
public class PhotoUtilController {

    /**
     * 封装返回结果
     */
    private final Result result;

    private final HttpUtil httpUtil;

    @PostMapping("/getCloudPhoto1")
    public ResultVo CloudPhotoUtilController() throws IOException {
        return result.success("123");
    }

    @PostMapping("/getCloudPhoto")
    public ResultVo CloudPhotoUtilController(@RequestParam String code) throws IOException {

        String urlV3 = "https://dycloud.onlineweixin.com/wallpaper/selectShareUserIdByPopuWordV3.action";

        String dataV3 = "popuWord=" + code;

        Header header = new BasicHeader("Referer", "https://tmaservice.developer.toutiao.com/?appid=tt89acff111696cb21&version=1.0.166");

        JSONObject resultV3 = JSONObject.parseObject(httpUtil.postUrl(urlV3, dataV3, header).getData().toString()).getJSONObject("data");

        String classId = resultV3.getString("classId");
        String popularizeId = resultV3.getString("popularizeId");
        String accId = resultV3.getString("accId");
        String queryType = resultV3.getString("queryType");

        String urlV4 = "https://dycloud.onlineweixin.com/wallpaper/selectEmoWallpaperHomePageV4.action";

        String dataV4 = "page=0" + "&limit=0" + "&classId=" + classId + "&popularizeId=" + popularizeId + "&shareUserId=&queryType=" + queryType + "&accId=" + accId + "&type=0";

        LogUtil.info("总长度：" + JSONObject.parseObject(httpUtil.postUrl(urlV4, dataV4, header).getData().toString()).getString("total"));

        JSONArray dataList = JSONObject.parseObject(httpUtil.postUrl(urlV4, dataV4, header).getData().toString()).getJSONArray("data");

        Map<String, List<String>> map = new HashMap<>();

        // 使用枚举类型初始化Map
        for (CloudPhotoType type : CloudPhotoType.values()) {
            map.put(type.getTypeName(), new ArrayList<>());
        }

        for (int i = 0; i < dataList.size(); i++) {
            JSONArray data = dataList.getJSONArray(i);
            for (int j = 0; j < data.size(); j++) {
                JSONObject jsonObject = data.getJSONObject(j);
                int id = jsonObject.getInteger("classId");
                String name = CloudPhotoType.getTypeNameByTypeId(id);
                String dynamicUrl = jsonObject.getString("dynamicUrl");
                int index = dynamicUrl.indexOf("?");

                if (index > -1) {
                    dynamicUrl = dynamicUrl.substring(0, index);
                }

                List<String> list = map.getOrDefault(name, new ArrayList<>());
                list.add(dynamicUrl);
                map.put(name, list);

            }
        }

        // 获取Map中的所有键值对
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            String typeName = entry.getKey();
            List<String> list = entry.getValue();
            LogUtil.info(typeName + "：" + list.size());
        }

        return result.success(map);

    }

}
