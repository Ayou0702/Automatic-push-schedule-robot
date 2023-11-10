package com.enterprise.controller.data;

import com.enterprise.common.handler.Result;
import com.enterprise.vo.data.entity.ScheduleData;
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
 * 负责课表数据的Controller
 *
 * @author PrefersMin
 * @version 1.6
 */
@RestController
@RequiredArgsConstructor
public class ScheduleDataController {

    /**
     * 课程数据接口
     */
    private final ScheduleDataService scheduleDataService;

    /**
     * 课表数据接口
     */
    private final CourseDataService courseDataService;

    /**
     * 事务管理器
     */
    private final PlatformTransactionManager platformTransactionManager;

    /**
     * 获取课表数据
     *
     * @author PrefersMin
     *
     * @return 返回获取到的课表数据
     */
    @GetMapping("/getScheduleData")
    public Result getScheduleData() {

        List<ScheduleData> scheduleDataList = scheduleDataService.queryAllScheduleData();

        if (scheduleDataList == null) {
            return Result.failed().message("课表数据加载失败");
        }

        return Result.success().message("课表数据加载成功").data("scheduleDataList", scheduleDataList);

    }

    /**
     * 更新课表数据
     *
     * @author PrefersMin
     *
     * @param scheduleData 需要更新的课表数据
     * @return 返回更新结果
     */
    @PostMapping("/updateScheduleData")
    public Result updateScheduleData(@RequestBody ScheduleData scheduleData) {

        String message;

        if (isNull(scheduleDataService.queryScheduleDataByScheduleId(scheduleData.getScheduleId()))) {
            message = "ID为" + scheduleData.getScheduleId() + "的课表数据更新失败,课表数据不存在";
            LogUtil.error(message);
            return Result.failed().code(400).message("更新课表数据失败").description(message);
        }

        boolean updateResult = scheduleDataService.updateScheduleData(scheduleData);

        if (updateResult) {
            message = "课表ID为 " + scheduleData.getScheduleId() + " 的课表数据被修改";
            LogUtil.info(message);
            return Result.success().message("修改课表数据成功").description(message);
        }

        LogUtil.error("ID为" + scheduleData.getScheduleId() + "的课表数据修改失败");

        return Result.failed().message("修改课表数据失败").description("ID为" + scheduleData.getScheduleId() + "的课表数据修改失败");

    }

    /**
     * 删除课表数据
     *
     * @author PrefersMin
     *
     * @param scheduleIdList 需要删除的课表ID列表
     * @return 返回删除结果
     */
    @PostMapping("/deleteScheduleData")
    public Result deleteScheduleData(@RequestBody List<Integer> scheduleIdList) {

        // 开始事务
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(new DefaultTransactionDefinition());

        // 记录操作结果
        List<String> record = new ArrayList<>();
        List<String> failedRecord = new ArrayList<>();
        String courseName = courseDataService.queryCourseDataByCourseId(scheduleDataService.queryScheduleDataByScheduleId(scheduleIdList.get(0)).getCourseId()).getCourseName();

        for (int scheduleId : scheduleIdList) {
            if (isNull(scheduleDataService.queryScheduleDataByScheduleId(scheduleId))) {
                LogUtil.error("ID为" + scheduleId + "的课表数据删除失败,课表不存在");
                failedRecord.add("ID为" + scheduleId + "的课表数据删除失败,课表不存在，请刷新页面");
            }
        }

        if (!failedRecord.isEmpty()) {
            failedRecord.forEach(LogUtil::error);
            // 回滚事务
            platformTransactionManager.rollback(transactionStatus);
            return Result.failed().message("删除课表数据失败").data("failedRecord", failedRecord);
        }

        for (int scheduleId : scheduleIdList) {
            boolean deleteResult = scheduleDataService.deleteScheduleData(scheduleId);
            if (!deleteResult) {
                LogUtil.error("ID为" + scheduleId + "的课表数据删除失败");
                // 回滚事务
                platformTransactionManager.rollback(transactionStatus);
                return Result.failed().message("删除课表数据失败").description("ID为" + scheduleId + "的课表数据删除失败");
            } else {
                record.add("ID为" + scheduleId + "的课表数据删除成功");
            }
        }

        record.forEach(LogUtil::info);
        LogUtil.info(scheduleIdList.size() + "条课表数据被删除");

        // 提交事务
        platformTransactionManager.commit(transactionStatus);

        if (scheduleIdList.size() > 1) {
            return Result.success().message("删除课表数据成功").description(scheduleIdList.size() + "条课表数据被删除");
        }

        return Result.success().message("删除课表数据成功").description(courseName + " 已从课表中删除");

    }

    /**
     * 新增课表数据
     *
     * @author PrefersMin
     *
     * @param scheduleData 需要新增的课表数据
     * @return 返回新增结果
     */
    @PostMapping("/addScheduleData")
    public Result addScheduleData(@RequestBody ScheduleData scheduleData) {

        boolean addResult = scheduleDataService.addScheduleData(scheduleData);

        String courseName = courseDataService.queryCourseDataByCourseId(scheduleData.getCourseId()).getCourseName();

        if (addResult) {
            LogUtil.info("新增课表数据，课表数据：" + scheduleData);
            return Result.success().message("新增课表数据成功").description(courseName + " 已添加到课表");
        }

        LogUtil.error("新增课表数据失败，课表数据：" + scheduleData);

        return Result.failed().message("新增课表数据失败").description(courseName + " 添加失败");

    }

}
