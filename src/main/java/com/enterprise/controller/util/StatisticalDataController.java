package com.enterprise.controller.util;

import com.enterprise.common.handler.Result;
import com.enterprise.service.data.entity.EnterpriseDataService;
import com.enterprise.util.PushDataUtil;
import com.enterprise.vo.pojo.ParameterListVo;
import com.enterprise.vo.pojo.WeatherVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 负责统计数据的Controller
 *
 * @author PrefersMin
 * @version 1.1
 */
@RestController
@RequiredArgsConstructor
public class StatisticalDataController {

    /**
     * 配置数据接口
     */
    private final EnterpriseDataService enterpriseDataService;

    /**
     * 推送数据的工具类
     */
    private final PushDataUtil pushDataUtil;

    @GetMapping("/getStatisticalData")
    public Result getStatisticalData() {

        ParameterListVo parameterList = pushDataUtil.getParameterList();

        int dateStarting = parameterList.getDateStarting();
        int dateEnding = parameterList.getDateEnding();
        String classDays = enterpriseDataService.queryingEnterpriseData("classDays").getDataValue();
        String morningClassDays = enterpriseDataService.queryingEnterpriseData("morningClassDays").getDataValue();
        String nightClassDays = enterpriseDataService.queryingEnterpriseData("nightClassDays").getDataValue();
        String totalClassTimes = enterpriseDataService.queryingEnterpriseData("totalClassTimes").getDataValue();
        String totalSpecializedClassTimes = enterpriseDataService.queryingEnterpriseData("totalSpecializedClassTimes").getDataValue();
        WeatherVo weather = parameterList.getWeatherVo();

        return Result.success().message("数据加载成功")
                .description("统计数据加载成功")
                .data("dateStarting", dateStarting)
                .data("dateEnding", dateEnding)
                .data("classDays", classDays)
                .data("morningClassDays", morningClassDays)
                .data("nightClassDays", nightClassDays)
                .data("totalClassTimes", totalClassTimes)
                .data("totalSpecializedClassTimes", totalSpecializedClassTimes)
                .data("weather", weather);
    }

}
