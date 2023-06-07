package com.enterprise.controller;

import com.enterprise.entity.CourseData;
import com.enterprise.entity.vo.ResultVo;
import com.enterprise.exception.CustomException;
import com.enterprise.service.CourseDataService;
import com.enterprise.service.ScheduleDataService;
import com.enterprise.util.LogUtil;
import com.enterprise.util.Result;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

/**
 * 负责课程数据的Controller
 *
 * @author PrefersMin
 * @version 1.0
 */
@RestController
public class CourseDataController {

    @Resource
    Result result;

    @Resource
    CourseDataService courseDataService;

    @Resource
    ScheduleDataService scheduleDataService;

    /**
     * 获取课程数据
     *
     * @author PrefersMin
     *
     * @return 返回获取到的课程数据
     */
    @GetMapping("/getCourseData")
    public ResultVo getCourseData() {

        List<CourseData> courseDataList = courseDataService.queryAllCourseData();

        if (courseDataList == null) {
            return result.failed("加载课程数据失败");
        }

        return result.success("加载课程数据成功", courseDataList);

    }

    /**
     * 更新课程数据
     *
     * @author PrefersMin
     *
     * @param courseData 需要更新的课程数据
     * @return 返回更新结果
     */
    @Transactional
    @PostMapping("/updateCourseData")
    public ResultVo updateCourseData(@RequestBody CourseData courseData) {

        try {

            String message;

            if (isNull(courseDataService.queryCourseDataByCourseId(courseData.getCourseId()))) {

                message = "ID为" + courseData.getCourseId() + "的课程数据更新失败,课程数据不存在";
                LogUtil.error(message);
                return result.failed(400, "更新课程数据失败", message);

            } else {

                boolean updateResult = courseDataService.updateCourseData(courseData);

                if (!updateResult) {

                    LogUtil.error("ID为" + courseData.getCourseId() + "的课程数据修改失败");
                    throw new CustomException("修改课程数据失败");

                } else {

                    message = "课程ID为 " + courseData.getCourseId() + " 的课程数据被修改";
                    LogUtil.info(message);
                    return result.success(200, "修改课程数据成功", message);

                }

            }

        } catch (CustomException e) {

            LogUtil.error(e.getMessage());
            return result.failed(400, e.getMessage(), "ID为" + courseData.getCourseId() + "的课程数据修改失败");

        }

    }

    /**
     * 删除课程数据
     *
     * @author PrefersMin
     *
     * @param courseIdList 需要删除的课程ID列表
     * @return 返回删除结果
     */
    @Transactional
    @PostMapping("/deleteCourseData")
    public ResultVo deleteCourseData(@RequestBody List<Integer> courseIdList) {

        // 记录操作结果
        List<String> record = new ArrayList<>();
        List<String> failedRecord = new ArrayList<>();

        try {

            for (int courseId : courseIdList) {

                if (isNull(courseDataService.queryCourseDataByCourseId(courseId))) {

                    LogUtil.error("ID为" + courseId + "的课程数据删除失败,课程不存在");
                    failedRecord.add("ID为" + courseId + "的课程数据删除失败,课程不存在，请刷新页面");

                }

                if (!scheduleDataService.queryScheduleDataByCourseId(courseId).isEmpty()) {

                    LogUtil.error("ID为" + courseId + "的课程数据删除失败,外键完整性约束检查失败");
                    failedRecord.add(courseDataService.queryCourseDataByCourseId(courseId).getCourseName() + " 已在课表中使用，不可删除");

                }

            }

            if (failedRecord.size() > 0) {

                failedRecord.forEach(LogUtil::error);
                return result.failed(400, "删除课程数据失败", failedRecord);

            } else {

                for (int courseId : courseIdList) {

                    boolean deleteResult = courseDataService.deleteCourseData(courseId);

                    if (!deleteResult) {

                        LogUtil.error("ID为" + courseId + "的课程数据删除失败");
                        throw new CustomException("ID为" + courseId + "的课程数据删除失败");

                    } else {

                        record.add("ID为" + courseId + "的课程数据删除成功");

                    }

                }

            }

            record.forEach(LogUtil::info);
            LogUtil.info(courseIdList.size() + "条课程数据被删除");

            return result.success(200, "删除课程数据成功", courseIdList.size() + "条课程数据被删除");

        } catch (CustomException e) {

            LogUtil.error(e.getMessage());
            return result.failed(400, "删除课程数据失败", e.getMessage());

        }

    }

    /**
     * 新增课程数据
     *
     * @author PrefersMin
     *
     * @param courseData 需要新增的课程数据
     * @return 返回新增结果
     */
    @Transactional
    @PostMapping("/addCourseData")
    public ResultVo addCourseData(@RequestBody CourseData courseData) {

        try {

            boolean addResult = courseDataService.addCourseData(courseData);

            if (!addResult) {

                LogUtil.error("新增课程数据失败，课程数据：" + courseData);
                throw new CustomException("新增课程数据失败");

            } else {

                LogUtil.info("新增课程数据，课程数据：" + courseData);
                return result.success(200, "新增课程数据成功", "课程名称为 " + courseData.getCourseName() + " 的课程数据新增成功");

            }

        } catch (CustomException e) {

            LogUtil.error(e.getMessage());
            return result.failed(400, e.getMessage(), "课程名称为 " + courseData.getCourseName() + " 的课程数据新增失败");

        }

    }

}