package com.enterprise.util;

import com.enterprise.entity.vo.ParameterListVo;
import com.enterprise.service.EnterpriseDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.enterprise.util.DateUtil.getNow;

/**
 * 推送数据的工具类
 *
 * @author PrefersMin
 * @version 1.7
 */
@Component
@RequiredArgsConstructor
public class PushDataUtil {

    /**
     * 最大周数、星期、节数
     */
    public static final int PERIOD_MAX = 23;
    public static final int WEEK_MAX = 8;
    public static final int SECTION_MAX = 6;

    /**
     * 配置数据接口
     */
    private final EnterpriseDataService enterpriseDataService;

    /**
     * 声明天气参数、天行数据api密钥、开学日期、放假日期、高德api密钥
     */
    public String weatherValue, tianApiKey, dateEnding, dateStarting, amapKey;

    /**
     * 获取推送模式
     *
     * @author PrefersMin
     *
     * @return 返回推送模式
     */
    public int getPushMode() {
        return Integer.parseInt(enterpriseDataService.queryingEnterpriseData("pushMode").getDataValue());
    }

    /**
     * 获取天气位置、彩虹屁api、开学日期、放假日期、推送时间并写入对象
     *
     * @author PrefersMin
     *
     * @return 返回参数列表对象
     */
    public ParameterListVo getParameterList() {

        // 获取数据
        weatherValue = enterpriseDataService.queryingEnterpriseData("weatherValue").getDataValue();
        tianApiKey = enterpriseDataService.queryingEnterpriseData("tianApiKey").getDataValue();
        dateEnding = enterpriseDataService.queryingEnterpriseData("dateEnding").getDataValue();
        dateStarting = enterpriseDataService.queryingEnterpriseData("dateStarting").getDataValue();
        amapKey = enterpriseDataService.queryingEnterpriseData("amapKey").getDataValue();

        // 参数列表实体类
        ParameterListVo parameterList = new ParameterListVo();

        parameterList.setWeatherVo(ApiUtil.getWeather(amapKey, weatherValue, getPushMode()));
        parameterList.setCaiHongPi(ApiUtil.getCaiHongPi(tianApiKey));
        parameterList.setDateEnding(DateUtil.daysBetween(getNow(), dateEnding));
        parameterList.setDateStarting(DateUtil.daysBetween(dateStarting, getNow()) + getPushMode());

        LogUtil.info("获取了参数列表：" + parameterList);
        return parameterList;

    }

}
