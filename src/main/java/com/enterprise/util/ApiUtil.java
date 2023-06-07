package com.enterprise.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.enterprise.service.EnterpriseDataService;
import com.enterprise.service.impl.WxCoreServiceImpl;
import com.enterprise.entity.vo.UserListVo;
import com.enterprise.entity.vo.WeatherVo;
import com.enterprise.util.enums.PushMode;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * api工具类
 *
 * @author PrefersMin
 * @version 1.6
 */
@Component
public class ApiUtil {

    /**
     * 微信核心服务
     */
    @Resource
    WxCoreServiceImpl wxCoreService;

    /**
     * 企业数据接口
     */
    @Resource
    EnterpriseDataService enterpriseDataService;

    /**
     * 获取彩虹屁
     *
     * @author PrefersMin
     *
     * @param key 天行数据彩虹屁api密钥
     * @return 返回彩虹屁
     */
    public static String getCaiHongPi(String key) {

        // 固定请求地址，详见 https://www.tianapi.com/apiview/181
        String url = "https://api.tianapi.com/pyqwenan/index?key=";
        String str = "阳光落在屋里，爱你藏在心里";

        try {
            JSONObject jsonObject = JSONObject.parseObject(HttpUtil.getUrl(url + key));
            assert jsonObject != null;
            if (jsonObject.getIntValue("code") == 200) {
                // 转换返回json数据
                str = jsonObject.getJSONArray("newslist").getJSONObject(0).getString("content");
                return str;
            }
            LogUtil.error("返回码错误：获取彩虹屁失败");
        } catch (IOException e) {
            LogUtil.error("try异常：获取彩虹屁失败");
            e.printStackTrace();
        }
        LogUtil.error("try失败：获取彩虹屁失败");
        return str;

    }

    /**
     * 获取天气数据
     *
     * @author PrefersMin
     *
     * @param key 天行数据天气预报api密钥
     * @param city 需要预报的城市
     * @param pushMode 推送时间
     * @return 返回天气实体对象
     */
    public static WeatherVo getWeather(String key, String city, int pushMode) {

        JSONObject jsonObject;
        // 固定请求地址，详见 https://lbs.amap.com/api/webservice/guide/api/weatherinfo/#t1
        String url = "https://restapi.amap.com/v3/weather/weatherInfo?key=";
        // 清空一下
        WeatherVo weatherVo = new WeatherVo();

        try {
            // 根据推送时间获取天气
            jsonObject = JSONObject.parseObject(HttpUtil.getUrl(url + key + "&city=" + city + "&extensions=all"));
            assert jsonObject != null;
            weatherVo = JSON.parseObject(jsonObject.getJSONArray("forecasts").getJSONObject(0).getJSONArray("casts").getJSONObject(pushMode).toString(), WeatherVo.class);
            weatherVo.setArea(jsonObject.getJSONArray("forecasts").getJSONObject(0).getString("city"));
            LogUtil.info((PushMode.NIGHT.getValue() == pushMode) ? "当前推送的是明日天气" : "当前推送的是今日天气");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return weatherVo;
    }

    /**
     * 获取部门id下所有成员的列表
     *
     * @author PrefersMin
     *
     * @return 返回一串拼接好的成员String
     */
    public String getParticipants() {

        // 固定请求地址，详见 https://developer.work.weixin.qq.com/document/path/90200
        String url = "https://qyapi.weixin.qq.com/cgi-bin/user/simplelist?access_token=";

        try {
            // 发送GET请求
            String sendGet = HttpUtil.getUrl(url + wxCoreService.getAccessToken() + "&department_id=" + enterpriseDataService.queryingEnterpriseData("departmentId").getDataValue());

            // 转换返回json数据
            JSONObject jsonObject = JSONObject.parseObject(sendGet);

            // 判断是否请求成功
            assert jsonObject != null;
            if (jsonObject.getIntValue("errcode") == 0) {
                // 截取userlist部门成员对象数组
                JSONArray participants = jsonObject.getJSONArray("userlist");
                // 通过部门成员类获取成员name与userid
                List<UserListVo> userListVo = participants.toJavaList(UserListVo.class);

                // 遍历拼接成员userid
                StringBuilder temp = new StringBuilder();
                for (UserListVo userlist : userListVo) {
                    if (!temp.toString().isEmpty()) {
                        temp.append("|");
                    }
                    temp.append(userlist.getUserid());
                }
                return temp.toString();
            }
            LogUtil.error("返回码错误：获取部门成员列表失败");
        } catch (IOException e) {
            LogUtil.error("try异常：获取部门成员列表失败");
            e.printStackTrace();
        }
        LogUtil.error("try失败：获取部门成员列表失败");
        return null;
    }

}
