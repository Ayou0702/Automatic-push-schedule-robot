package com.enterprise.util;

import com.enterprise.entity.CourseData;
import com.enterprise.entity.CurriculumData;
import com.enterprise.service.CourseDataService;
import com.enterprise.service.CurriculumDataService;
import com.enterprise.service.EnterpriseDataService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

import static java.util.Objects.isNull;

@Component
public class CourseDataUtil {

    @Resource
    DateUtil dateUtil;
    @Resource
    CurriculumDataService curriculumDataService;
    @Resource
    private CourseDataService courseDataService;

    @Resource
    private EnterpriseDataService enterpriseDataService;

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

    public CourseData[] getTodayCourseData(List<CurriculumData> curriculumDataList) {

        CourseData[] courseDataArray = new CourseData[PushDataUtil.SECTION_MAX];

        curriculumDataList.forEach(curriculumData -> {
            if (!isNull(curriculumData)) {

                int section = curriculumData.getCurriculumSection();
                courseDataArray[section] = courseDataService.queryCourseDataByCourseId(curriculumData.getCourseId());

            }
        });

        return courseDataArray;
    }

}
