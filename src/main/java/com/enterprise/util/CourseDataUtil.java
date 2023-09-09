package com.enterprise.util;

import com.enterprise.service.EnterpriseDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 课程数据工具类
 *
 * @author PrefersMin
 * @version 1.3
 */
@Component
@RequiredArgsConstructor
public class CourseDataUtil {

    /**
     * 配置数据接口
     */
    private final EnterpriseDataService enterpriseDataService;

    /**
     * 配置数据工具类
     */
    private final EnterpriseDataUtil enterpriseDataUtil;

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

        enterpriseDataUtil.dataIncrement("totalClassTimes");

    }

}