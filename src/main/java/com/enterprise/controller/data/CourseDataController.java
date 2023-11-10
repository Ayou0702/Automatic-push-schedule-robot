package com.enterprise.controller.data;

import com.enterprise.common.handler.Result;
import com.enterprise.vo.data.entity.CourseData;
import com.enterprise.service.data.entity.CourseDataService;
import com.enterprise.service.data.entity.ScheduleDataService;
import com.enterprise.util.LogUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

/**
 * 负责课程数据的Controller
 *
 * @author PrefersMin
 * @version 1.5
 */
@RestController
@RequiredArgsConstructor
public class CourseDataController {

    /**
     * 课程数据接口
     */
    private final CourseDataService courseDataService;

    /**
     * 课表数据接口
     */
    private final ScheduleDataService scheduleDataService;

    /**
     * 事务管理器
     */
    private final PlatformTransactionManager platformTransactionManager;

    /**
     * 查询所有课程数据
     *
     * @author PrefersMin
     *
     * @return 返回查询到的课程数据
     */
    @GetMapping("/getCourseData")
    public Result getCourseData() {

        List<CourseData> courseDataList = courseDataService.queryAllCourseData();

        if (courseDataList == null) {
            return Result.failed().message("加载课程数据失败");
        }

        return Result.success().message("加载课程数据成功").data("courseDataList", courseDataList);

    }

    /**
     * 更新课程数据
     *
     * @author PrefersMin
     *
     * @param courseData 需要更新的课程数据
     * @return 返回更新结果
     */
    @PostMapping("/updateCourseData")
    public Result updateCourseData(@RequestBody CourseData courseData) {

        String message;

        if (isNull(courseDataService.queryCourseDataByCourseId(courseData.getCourseId()))) {
            message = "ID为" + courseData.getCourseId() + "的课程数据更新失败,课程数据不存在";
            LogUtil.error(message);
            return Result.failed().message("更新课程数据失败").description(message);
        }

        boolean updateResult = courseDataService.updateCourseData(courseData);

        if (updateResult) {
            message = "课程ID为 " + courseData.getCourseId() + " 的课程数据被修改";
            LogUtil.info(message);
            return Result.success().message("修改课程数据成功").description(message);
        }

        LogUtil.error("ID为" + courseData.getCourseId() + "的课程数据修改失败");

        return Result.failed().message("修改课程数据失败").description("ID为" + courseData.getCourseId() + "的课程数据修改失败");

    }

    /**
     * 删除课程数据
     *
     * @author PrefersMin
     *
     * @param courseIdList 需要删除的课程ID列表
     * @return 返回删除结果
     */
    @PostMapping("/deleteCourseData")
    public Result deleteCourseData(@RequestBody List<Integer> courseIdList) {

        // 开始事务
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(new DefaultTransactionDefinition());

        // 记录操作结果
        List<String> record = new ArrayList<>();
        List<String> failedRecord = new ArrayList<>();

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

        if (!failedRecord.isEmpty()) {
            failedRecord.forEach(LogUtil::error);
            // 回滚事务
            platformTransactionManager.rollback(transactionStatus);
            return Result.failed().message("删除课程数据失败").data("failedRecord",failedRecord);
        }

        for (int courseId : courseIdList) {
            boolean deleteResult = courseDataService.deleteCourseData(courseId);
            if (!deleteResult) {
                LogUtil.error("ID为" + courseId + "的课程数据删除失败");
                // 回滚事务
                platformTransactionManager.rollback(transactionStatus);
                return Result.failed().message("删除课程数据失败").description("ID为" + courseId + "的课程数据删除失败");
            }
            record.add("ID为" + courseId + "的课程数据删除成功");
        }

        record.forEach(LogUtil::info);
        LogUtil.info(courseIdList.size() + "条课程数据被删除");
        // 提交事务
        platformTransactionManager.commit(transactionStatus);

        if (courseIdList.size() > 1) {
            return Result.success().message("删除课程数据成功").description(courseIdList.size() + "条课程数据被删除");
        }

        return Result.success().message("课程数据删除成功").description("ID为 " + courseIdList.get(0) + " 的课程数据被删除");

    }

    /**
     * 新增课程数据
     *
     * @author PrefersMin
     *
     * @param courseData 需要新增的课程数据
     * @return 返回新增结果
     */
    @PostMapping("/addCourseData")
    public Result addCourseData(@RequestBody CourseData courseData) {

        boolean addResult = courseDataService.addCourseData(courseData);

        if (addResult) {
            LogUtil.info("新增课程数据，课程数据：" + courseData);
            return Result.success().message("新增课程数据成功").description("课程名称为 " + courseData.getCourseName() + " 的课程数据新增成功");
        }

        LogUtil.error("新增课程数据失败，课程数据：" + courseData);

        return Result.failed().message("新增课程数据失败").description("课程名称为 " + courseData.getCourseName() + " 的课程数据新增失败");

    }

}
