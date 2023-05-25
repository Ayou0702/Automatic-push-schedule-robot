package com.enterprise.controller;

import com.alibaba.fastjson2.JSONObject;
import com.enterprise.entity.CourseData;
import com.enterprise.entity.vo.ResultVo;
import com.enterprise.exception.CustomException;
import com.enterprise.service.CourseDataService;
import com.enterprise.service.ScheduleDataService;
import com.enterprise.util.LogUtil;
import com.enterprise.util.Result;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.util.Objects.isNull;

@RestController
public class CourseDataController {

    @Resource
    Result result;

    @Resource
    CourseDataService courseDataService;

    @Resource
    ScheduleDataService scheduleDataService;

    @Resource
    PlatformTransactionManager platformTransactionManager;

    List<Integer> deleteCourseData;
    List<CourseData> diffCourse;
    List<CourseData> addCourseData;

    @GetMapping("/getCourseData")
    public ResultVo getCourseData() {

        List<CourseData> courseDataList = courseDataService.queryAllCourseData();

        if (courseDataList.isEmpty()) {
            return result.failed("课程数据加载失败");
        }

        return result.success("课程数据加载成功", courseDataList);

    }

    @PostMapping("/modifyCourseData")
    public ResultVo modifyCourseData(@RequestBody String courseData) {

        // 开始事务
        TransactionStatus transaction = platformTransactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            deleteCourseData = JSONObject.parseObject(courseData).getJSONArray("deleteCourseData").toList(Integer.class);
            diffCourse = JSONObject.parseObject(courseData).getJSONArray("diffCourse").toJavaList(CourseData.class);
            addCourseData = JSONObject.parseObject(courseData).getJSONArray("addCourseData").toJavaList(CourseData.class);
        } catch (Exception e) {
            return result.failed(e.getMessage());
        }

        try {

            // 进行数据预检
            previewingData();

            if (!deleteCourseData.isEmpty()) {
                deleteCourseData(deleteCourseData);
            }

            if (!diffCourse.isEmpty()) {
                updateCourseData(diffCourse);
            }

            if (!addCourseData.isEmpty()) {
                addCourseData(addCourseData);
            }

            platformTransactionManager.commit(transaction);
        } catch (CustomException e) {
            LogUtil.error("触发了CustomException：" + e.getMessage());
            platformTransactionManager.rollback(transaction);
            return result.failed(e.getExceptionVo().getErrorCode(), e.getExceptionVo().getErrorMessage(), e.getExceptionVo().getErrorState());

        } catch (Exception e) {
            LogUtil.error("触发了Exception：" + e.getMessage());
            platformTransactionManager.rollback(transaction);
            return result.failed(e.getMessage());
        }
        return result.success("课程数据修改成功");

    }

    public void updateCourseData(List<CourseData> courseDataList) {
        List<String> record = new ArrayList<>();
        try {
            for (CourseData courseData : courseDataList) {
                boolean updateResult = courseDataService.updateCourseData(courseData);
                if (!updateResult) {
                    LogUtil.error("ID为" + courseData.getCourseId() + "的课程信息修改失败");
                    throw new CustomException("修改课程信息失败,操作已回滚");
                } else {
                    record.add("课程信息被修改，课程信息：" + courseData);
                }
            }
            record.forEach(LogUtil::info);
            LogUtil.info(courseDataList.size() + "条课程数据被修改");
        } catch (CustomException e) {
            LogUtil.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }


    public void deleteCourseData(List<Integer> courseIdList) {
        List<String> record = new ArrayList<>();
        try {
            for (int courseId : courseIdList) {
                boolean deleteResult = courseDataService.deleteCourseData(courseId);
                if (!deleteResult) {
                    LogUtil.error("ID为" + courseId + "的课程信息删除失败");
                    throw new CustomException("删除课程信息失败,操作已回滚");
                } else {
                    record.add("课程信息被删除，课程信息：" + courseId);
                }
            }
            record.forEach(LogUtil::info);
            LogUtil.info(courseIdList.size() + "条课程数据被删除");
        } catch (CustomException e) {
            LogUtil.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public void addCourseData(List<CourseData> courseDataList) {
        List<String> record = new ArrayList<>();
        try {
            for (CourseData courseData : courseDataList) {
                boolean addResult = courseDataService.addCourseData(courseData);
                if (!addResult) {
                    LogUtil.error("新增课程信息失败，课程信息：" + courseData);
                    throw new CustomException("新增课程信息失败,操作已回滚");
                } else {
                    record.add("新增课程信息，课程信息：" + courseData);
                }
            }
            record.forEach(LogUtil::info);
            LogUtil.info("新增" + courseDataList.size() + "条课程信息");
        } catch (CustomException e) {
            LogUtil.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public void previewingData() throws CustomException {

        // 创建一个可变字符串类作为容器用以追加字符串到消息序列
        StringBuilder message = new StringBuilder();

        AtomicBoolean deleteCourseDataPreviewingResult = new AtomicBoolean(false);
        AtomicBoolean diffCoursePreviewingResult = new AtomicBoolean(false);

        if (!deleteCourseData.isEmpty()) {
            deleteCourseData.forEach(courseId -> {
                if (!scheduleDataService.queryScheduleDataByCourseId(courseId).isEmpty()) {
                    LogUtil.error("ID为" + courseId + "的课程信息删除失败,外键完整性约束检查失败");
                    message.append(courseDataService.queryCourseDataByCourseId(courseId).getCourseName()).append(" 已在课表中使用，不可删除\n");
                    deleteCourseDataPreviewingResult.set(true);
                }
            });
        }

        if (!diffCourse.isEmpty()) {
            diffCourse.forEach(diffCourseData -> {
                if (isNull(courseDataService.queryCourseDataByCourseId(diffCourseData.getCourseId()))) {
                    LogUtil.error("ID为" + diffCourseData.getCourseId() + "的课程信息更新失败,课程信息不存在");
                    message.append(courseDataService.queryCourseDataByCourseId(diffCourseData.getCourseId()).getCourseName()).append(" 的课程信息不存在，请刷新页面\n");
                    diffCoursePreviewingResult.set(true);
                }
            });
        }

        if (deleteCourseDataPreviewingResult.get() || diffCoursePreviewingResult.get()) {
            LogUtil.error("数据预检不通过，操作已回滚");
            throw new CustomException(message.toString(), "数据预检不通过，请检查", 901);
        }

    }

}
