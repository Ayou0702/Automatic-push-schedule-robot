package com.enterprise.util;

import com.enterprise.entity.CourseData;
import com.enterprise.service.CourseDataService;
import com.enterprise.service.EnterpriseDataService;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 课程数据工具类
 *
 * @author PrefersMin
 * @version 1.1
 */
@Component
public class CourseDataUtil {

    /**
     * 课程数据接口
     */
    private final CourseDataService courseDataService;

    /**
     * 配置数据接口
     */
    private final EnterpriseDataService enterpriseDataService;

    /**
     * 构造器注入Bean
     *
     * @author PrefersMin
     *
     * @param courseDataService 课程数据接口
     * @param enterpriseDataService 配置数据接口
     */
    public CourseDataUtil(CourseDataService courseDataService, EnterpriseDataService enterpriseDataService) {
        this.courseDataService = courseDataService;
        this.enterpriseDataService = enterpriseDataService;
    }

    /**
     * 查询所有课程数据
     *
     * @author PrefersMin
     *
     * @return 返回查询结果
     */
    public List<CourseData> queryAllCourseData() {
        return courseDataService.queryAllCourseData();
    }

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