package com.enterprise.controller;

import com.enterprise.entity.CourseData;
import com.enterprise.entity.CurriculumData;
import com.enterprise.entity.TeacherData;
import com.enterprise.entity.vo.ResultVo;
import com.enterprise.service.CourseDataService;
import com.enterprise.service.CurriculumDataService;
import com.enterprise.service.TeacherDataService;
import com.enterprise.util.CurriculumDataUtil;
import com.enterprise.util.DateUtil;
import com.enterprise.util.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CurriculumDataController {

    @Resource
    Result result;

    @Resource
    DateUtil dateUtil;

    @Resource
    CurriculumDataUtil curriculumDataUtil;

    @Resource
    TeacherDataService teacherDataService;

    @Resource
    CourseDataService courseDataService;

    @Resource
    CurriculumDataService curriculumDataService;

    @GetMapping("/queryAllCurriculumData")
    public ResultVo queryAllCurriculumData() {

        List<CurriculumData> curriculumData = curriculumDataService.queryAllCurriculumData();

        if (curriculumData.isEmpty()) {
            return result.failed("课表数据加载失败");
        }

        return result.success("课表数据加载成功", curriculumData);

    }

    @GetMapping("/resetCurriculumData")
    public void resetCurriculumData() {
        curriculumDataUtil.resetCurriculumData();
    }

    @GetMapping("/queryNowCurriculumData")
    public ResultVo queryNowCurriculumData() {

        int week = dateUtil.getW();
        int period = dateUtil.getPeriod();

        System.out.println(period+"-"+week);

        List<CurriculumData> curriculumDataList = curriculumDataService.queryNowCurriculumData(period,week);

        List<CourseData> courseDataList = courseDataService.queryAllCourseIdAndCourseName();

        List<TeacherData> teacherDataList = teacherDataService.queryAllTeacherIdAndTeacherName();

        if (curriculumDataList.isEmpty()||courseDataList.isEmpty()||teacherDataList.isEmpty()) {
            return result.failed("课表数据加载失败");
        }

        Map<String, Object> map = new HashMap<>();
        map.put("curriculumDataList", curriculumDataList);
        map.put("courseDataList", courseDataList);
        map.put("teacherDataList", teacherDataList);

        return result.success("课表数据加载成功", map);


    }

}
