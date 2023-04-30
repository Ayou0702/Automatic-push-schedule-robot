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
     * enterpriseData的接口，用于读取查询企业微信配置数据
     */
    @Resource
    EnterpriseDataService enterpriseDataService;

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
        String weatherValue = enterpriseDataService.queryingEnterpriseData("weatherValue").getDataValue();
        String apiKey = enterpriseDataService.queryingEnterpriseData("apiKey").getDataValue();
        String dateEnding = enterpriseDataService.queryingEnterpriseData("dateEnding").getDataValue();
        String dateStarting = enterpriseDataService.queryingEnterpriseData("dateStarting").getDataValue();
        int pushTime = Integer.parseInt(enterpriseDataService.queryingEnterpriseData("pushTime").getDataValue());

        // 写入推送时间用以共享给其他方法
        parameterList.setPushTime(pushTime);

        // 非空判断
        if (StringUtils.isNotEmpty(apiKey) && StringUtils.isNotEmpty(weatherValue)) {
            // 获取天气信息
            parameterList.setWeatherVo(ApiUtil.getWeather(apiKey, weatherValue, parameterList.getPushTime()));
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
            parameterList.setDateStarting((DateUtil.daysBetween(dateStarting, getNow()) + parameterList.getPushTime()));
        }

        System.out.println("参数列表：" + parameterList);
        return parameterList;
    }


}
