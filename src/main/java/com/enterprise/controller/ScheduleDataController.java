package com.enterprise.controller;

import com.alibaba.fastjson2.JSONObject;
import com.enterprise.entity.CourseData;
import com.enterprise.entity.ScheduleData;
import com.enterprise.entity.TeacherData;
import com.enterprise.entity.vo.ResultVo;
import com.enterprise.service.CourseDataService;
import com.enterprise.service.ScheduleDataService;
import com.enterprise.service.TeacherDataService;
import com.enterprise.util.LogUtil;
import com.enterprise.util.Result;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ScheduleDataController {

    /**
     * 封装返回结果
     */
    @Resource
    Result result;

    @Resource
    ScheduleDataService scheduleDataService;

    @Resource
    PlatformTransactionManager platformTransactionManager;

    List<Integer> deleteScheduleData;
    List<ScheduleData> diffSchedule;
    List<ScheduleData> addScheduleData;

    @GetMapping("/getScheduleData")
    public ResultVo getScheduleData() {

        List<ScheduleData> scheduleDataList = scheduleDataService.queryAllScheduleData();

        if (scheduleDataList == null) {
            return result.failed("课表数据加载失败");
        }

        return result.success("课表数据加载成功", scheduleDataList);

    }

    @PostMapping("/modifyScheduleData")
    public ResultVo modifyScheduleData(@RequestBody String courseData) {

        // 开始事务
        TransactionStatus transaction = platformTransactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            deleteScheduleData = JSONObject.parseObject(courseData).getJSONArray("deleteScheduleData").toList(Integer.class);
            diffSchedule = JSONObject.parseObject(courseData).getJSONArray("diffSchedule").toJavaList(ScheduleData.class);
            addScheduleData = JSONObject.parseObject(courseData).getJSONArray("addScheduleData").toJavaList(ScheduleData.class);
        } catch (Exception e) {
            return result.failed(e.getMessage());
        }

        try {

            if (!deleteScheduleData.isEmpty()) {
                deleteScheduleData(deleteScheduleData);
            }

            if (!diffSchedule.isEmpty()) {
                updateScheduleData(diffSchedule);
            }

            if (!addScheduleData.isEmpty()) {
                addScheduleData(addScheduleData);
            }

            platformTransactionManager.commit(transaction);
        } catch (Exception e) {
            platformTransactionManager.rollback(transaction);
            return result.failed(e.getMessage());
        }
        return result.success("课表数据修改成功");

    }

    public void updateScheduleData(List<ScheduleData> scheduleDataList) {
        List<String> record = new ArrayList<>();
        try {
            for (ScheduleData courseData : scheduleDataList) {
                boolean updateResult = scheduleDataService.updateScheduleData(courseData);
                if (!updateResult) {
                    LogUtil.error("ID为" + courseData.getCourseId() + "的课表信息修改失败");
                    throw new Exception("修改课表信息失败,操作已回滚");
                } else {
                    record.add("课表信息被修改，课程信息：" + courseData);
                }
            }
            record.forEach(LogUtil::info);
            LogUtil.info(scheduleDataList.size() + "条课表数据被修改");
        } catch (Exception e) {
            LogUtil.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }


    public void deleteScheduleData(List<Integer> scheduleIdList) {
        List<String> record = new ArrayList<>();
        try {
            for (int scheduleId : scheduleIdList) {
                boolean deleteResult = scheduleDataService.deleteScheduleData(scheduleId);
                if (!deleteResult) {
                    LogUtil.error("ID为" + scheduleId + "的课表信息删除失败");
                    throw new Exception("删除课表信息失败,操作已回滚");
                } else {
                    record.add("课表信息被删除，课程信息：" + scheduleId);
                }
            }
            record.forEach(LogUtil::info);
            LogUtil.info(scheduleIdList.size() + "条课表数据被删除");
        } catch (Exception e) {
            LogUtil.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public void addScheduleData(List<ScheduleData> scheduleDataList) {
        List<String> record = new ArrayList<>();
        try {
            for (ScheduleData scheduleData : scheduleDataList) {
                boolean addResult = scheduleDataService.addScheduleData(scheduleData);
                if (!addResult) {
                    LogUtil.error("新增课表信息失败，课程信息：" + scheduleData);
                    throw new Exception("新增课表信息失败,操作已回滚");
                } else {
                    record.add("新增课表信息，课程信息：" + scheduleData);
                }
            }
            record.forEach(LogUtil::info);
            LogUtil.info("新增" + scheduleDataList.size() + "条课表信息");
        } catch (Exception e) {
            LogUtil.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

}
