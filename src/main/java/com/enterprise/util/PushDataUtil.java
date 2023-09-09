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
 * @version 1.8
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
     * Api工具类
     */
    private final ApiUtil apiUtil;

    /**
     * 声明开学日期、放假日期
     */
    public String dateEnding, dateStarting;

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
        dateEnding = enterpriseDataService.queryingEnterpriseData("dateEnding").getDataValue();
        dateStarting = enterpriseDataService.queryingEnterpriseData("dateStarting").getDataValue();

        // 参数列表实体类
        ParameterListVo parameterList = new ParameterListVo();

        parameterList.setWeatherVo(apiUtil.getWeather());
        parameterList.setCaiHongPi(apiUtil.getCaiHongPi());
        parameterList.setDateEnding(DateUtil.daysBetween(getNow(), dateEnding));
        parameterList.setDateStarting(DateUtil.daysBetween(dateStarting, getNow()) + getPushMode());

        LogUtil.info("获取了参数列表：" + parameterList);
        return parameterList;

    }

}
