package com.enterprise.util;

import com.enterprise.service.EnterpriseDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 课程数据工具类
 *
 * @author PrefersMin
 * @version 1.2
 */
@Component
@RequiredArgsConstructor
public class CourseDataUtil {

    /**
     * 配置数据接口
     */
    private final EnterpriseDataService enterpriseDataService;

    /**
     * 统计总课程数
     *
     * @author PrefersMin
     */
    public void courseCount() {

        // 判断是否是debug中，如是则不计算课程数
        if (enterpriseDataService.queryingEnterpriseData("debugPushMode").getDataValue().equals(enterpriseDataService.queryingEnterpriseData("departmentId").getDataValue())) {
            return;
        }

        // 获取当前总课程数
        int temp = Integer.parseInt(enterpriseDataService.queryingEnterpriseData("totalClassTimes").getDataValue());
        // 自增
        temp++;
        // 回写
        enterpriseDataService.updateEnterpriseDataByDataName("totalClassTimes", String.valueOf(temp));

    }

}