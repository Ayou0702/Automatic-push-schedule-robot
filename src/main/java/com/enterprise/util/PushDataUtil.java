package com.enterprise.util;

import com.enterprise.entity.vo.ParameterListVo;
import com.enterprise.service.EnterpriseDataService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static com.enterprise.util.DateUtil.getNow;

/**
 * 推送数据的工具类
 *
 * @author PrefersMin
 * @version 1.3
 */
@Component
public class PushDataUtil {

    /**
     * 最大周数、星期、节数
     */
    public static final int PERIOD_MAX = 23;
    public static final int WEEK_MAX = 8;
    public static final int SECTION_MAX = 6;

    /**
     * enterpriseData的接口，用于读取查询企业微信配置数据
     */
    /**
     * enterpriseData的接口，用于读取查询企业微信配置数据
     */
    @Resource
    EnterpriseDataService enterpriseDataService;

    String weatherValue, apiKey, dateEnding, dateStarting,amapKey;

    public int getPushTime() {
        return Integer.parseInt(enterpriseDataService.queryingEnterpriseData("pushTime").getDataValue());
    }

    /**
     * 获取天气位置、彩虹屁api、开学日期、放假日期、推送时间并写入对象
     *
     * @author PrefersMin
     *
     * @return 返回参数列表对象
     */
    public ParameterListVo getParameterList() {

        // 参数列表实体类
        ParameterListVo parameterList = new ParameterListVo();

        // 通过Service层获取数据
        weatherValue = enterpriseDataService.queryingEnterpriseData("weatherValue").getDataValue();
        apiKey = enterpriseDataService.queryingEnterpriseData("apiKey").getDataValue();
        dateEnding = enterpriseDataService.queryingEnterpriseData("dateEnding").getDataValue();
        dateStarting = enterpriseDataService.queryingEnterpriseData("dateStarting").getDataValue();
        amapKey = enterpriseDataService.queryingEnterpriseData("amapKey").getDataValue();

        // 非空判断
        if (StringUtils.isNotEmpty(apiKey) && StringUtils.isNotEmpty(weatherValue)) {
            // 获取天气信息
            parameterList.setWeatherVo(ApiUtil.getWeather(amapKey, weatherValue, getPushTime()));
        }
        if (StringUtils.isNotEmpty(apiKey)) {
            // 获取彩虹屁
            parameterList.setCaiHongPi(ApiUtil.getCaiHongPi(apiKey));
        }
        if (StringUtils.isNotEmpty(dateEnding)) {
            // 计算离放假的天数
            parameterList.setDateEnding(DateUtil.daysBetween(getNow(), dateEnding));
        }
        if (StringUtils.isNotEmpty(dateStarting)) {
            // 计算已经开学的天数,并根据推送时间偏移
            parameterList.setDateStarting((DateUtil.daysBetween(dateStarting, getNow()) + getPushTime()));
        }

        LogUtil.info("获取了网络参数列表：" + parameterList);

        return parameterList;
    }

}
