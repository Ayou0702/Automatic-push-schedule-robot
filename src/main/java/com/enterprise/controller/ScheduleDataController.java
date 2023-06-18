package com.enterprise.controller;

import com.enterprise.entity.ScheduleData;
import com.enterprise.entity.vo.ResultVo;
import com.enterprise.service.ScheduleDataService;
import com.enterprise.util.LogUtil;
import com.enterprise.util.Result;
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
 * @version 1.3
 */
@RestController
public class ScheduleDataController {

    /**
     * 封装返回结果
     */
    private final Result result;

    /**
     * 课表数据接口
     */
    private final ScheduleDataService scheduleDataService;

    /**
     * 事务管理器
     */
    private final PlatformTransactionManager platformTransactionManager;

    /**
     * 构造器注入Bean
     *
     * @author PrefersMin
     *
     * @param result 统一返回结果
     * @param scheduleDataService 课表数据接口
     * @param platformTransactionManager 事务管理器
     */
    public ScheduleDataController(Result result, ScheduleDataService scheduleDataService, PlatformTransactionManager platformTransactionManager) {
        this.result = result;
        this.scheduleDataService = scheduleDataService;
        this.platformTransactionManager = platformTransactionManager;
    }

    /**
     * 获取课表数据
     *
     * @author PrefersMin
     *
     * @return 返回获取到的课表数据
     */
    @GetMapping("/getScheduleData")
    public ResultVo getScheduleData() {

        List<ScheduleData> scheduleDataList = scheduleDataService.queryAllScheduleData();

        if (scheduleDataList == null) {
            return result.failed("课表数据加载失败");
        }

        return result.success("课表数据加载成功", scheduleDataList);

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
    public ResultVo updateScheduleData(@RequestBody ScheduleData scheduleData) {

        // 开始事务
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(new DefaultTransactionDefinition());

        String message;

        if (isNull(scheduleDataService.queryScheduleDataByScheduleId(scheduleData.getScheduleId()))) {
            message = "ID为" + scheduleData.getScheduleId() + "的课表数据更新失败,课表数据不存在";
            LogUtil.error(message);
            return result.failed(400, "更新课表数据失败", message);
        }

        boolean updateResult = scheduleDataService.updateScheduleData(scheduleData);

        if (updateResult) {
            message = "课表ID为 " + scheduleData.getScheduleId() + " 的课表数据被修改";
            LogUtil.info(message);
            // 提交事务
            platformTransactionManager.commit(transactionStatus);
            return result.success(200, "修改课表数据成功", message);
        }

        LogUtil.error("ID为" + scheduleData.getScheduleId() + "的课表数据修改失败");
        // 回滚事务
        platformTransactionManager.rollback(transactionStatus);
        return result.failed(400, "修改课表数据失败", "ID为" + scheduleData.getScheduleId() + "的课表数据修改失败");

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
    public ResultVo deleteScheduleData(List<Integer> scheduleIdList) {

        // 开始事务
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(new DefaultTransactionDefinition());

        // 记录操作结果
        List<String> record = new ArrayList<>();
        List<String> failedRecord = new ArrayList<>();

        for (int scheduleId : scheduleIdList) {
            if (isNull(scheduleDataService.queryScheduleDataByScheduleId(scheduleId))) {
                LogUtil.error("ID为" + scheduleId + "的课表数据删除失败,课表不存在");
                failedRecord.add("ID为" + scheduleId + "的课表数据删除失败,课表不存在，请刷新页面");
            }
        }

        if (failedRecord.size() > 0) {
            failedRecord.forEach(LogUtil::error);
            // 回滚事务
            platformTransactionManager.rollback(transactionStatus);
            return result.failed(400, "删除课表数据失败", failedRecord);
        }

        for (int scheduleId : scheduleIdList) {
            boolean deleteResult = scheduleDataService.deleteScheduleData(scheduleId);
            if (!deleteResult) {
                LogUtil.error("ID为" + scheduleId + "的课表数据删除失败");
                // 回滚事务
                platformTransactionManager.rollback(transactionStatus);
                return result.failed(400, "删除课表数据失败", "ID为" + scheduleId + "的课表数据删除失败");
            } else {
                record.add("ID为" + scheduleId + "的课表数据删除成功");
            }
        }

        record.forEach(LogUtil::info);
        LogUtil.info(scheduleIdList.size() + "条课表数据被删除");
        // 提交事务
        platformTransactionManager.commit(transactionStatus);
        return result.success(200, "删除课表数据成功", scheduleIdList.size() + "条课表数据被删除");

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
    public ResultVo addScheduleData(@RequestBody ScheduleData scheduleData) {
        
        // 开始事务
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(new DefaultTransactionDefinition());

        boolean addResult = scheduleDataService.addScheduleData(scheduleData);

        if (addResult) {
            LogUtil.info("新增课表数据，课表数据：" + scheduleData);
            // 回滚事务
            platformTransactionManager.rollback(transactionStatus);
            return result.success(200, "新增课表数据成功", "ID为 " + scheduleData.getScheduleId() + " 的课表数据新增成功");
        }

        LogUtil.error("新增课表数据失败，课表数据：" + scheduleData);
        // 回滚事务
        platformTransactionManager.rollback(transactionStatus);
        return result.failed(400, "新增课表数据失败", "ID为 " + scheduleData.getScheduleId() + " 的课表数据新增失败");

    }

}
