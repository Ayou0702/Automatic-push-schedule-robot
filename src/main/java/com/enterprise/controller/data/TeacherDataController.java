package com.enterprise.controller.data;

import com.enterprise.entity.TeacherData;
import com.enterprise.entity.vo.ResultVo;
import com.enterprise.service.ScheduleDataService;
import com.enterprise.service.TeacherDataService;
import com.enterprise.util.LogUtil;
import com.enterprise.util.Result;
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
 * 负责教师数据的Controller
 *
 * @author PrefersMin
 * @version 1.3
 */
@RestController
@RequiredArgsConstructor
public class TeacherDataController {

    /**
     * 封装返回结果
     */
    private final Result result;

    /**
     * 教师数据接口
     */
    private final TeacherDataService teacherDataService;

    /**
     * 课表数据接口
     */
    private final ScheduleDataService scheduleDataService;

    /**
     * 事务管理器
     */
    private final PlatformTransactionManager platformTransactionManager;

    /**
     * 获取所有教师数据
     *
     * @author PrefersMin
     *
     * @return 返回查询到的教师数据
     */
    @GetMapping("/getTeacherData")
    public ResultVo getTeacherData() {

        List<TeacherData> teacherDataList = teacherDataService.queryAllTeacherData();

        if (teacherDataList == null) {
            return result.failed("教师数据加载失败");
        }

        return result.success("教师数据加载成功", teacherDataList);

    }

    /**
     * 更新指定教师数据
     *
     * @author PrefersMin
     *
     * @param teacherData 需要更新的教师数据
     * @return 返回更新结果
     */
    @PostMapping("/updateTeacherData")
    public ResultVo updateTeacherData(@RequestBody TeacherData teacherData) {

        // 开始事务
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(new DefaultTransactionDefinition());

        String message;

        if (isNull(teacherDataService.queryTeacherDataByTeacherId(teacherData.getTeacherId()))) {

            message = "ID为" + teacherData.getTeacherId() + "的教师数据更新失败,教师数据不存在";
            LogUtil.error(message);
            // 回滚事务
            platformTransactionManager.rollback(transactionStatus);
            return result.failed(400, "更新教师数据失败", message);

        }

        boolean updateResult = teacherDataService.updateTeacherData(teacherData);

        if (updateResult) {
            message = "ID为 " + teacherData.getTeacherId() + " 的教师数据被修改";
            LogUtil.info(message);
            // 提交事务
            platformTransactionManager.commit(transactionStatus);
            return result.success(200, "修改教师数据成功", message);
        }

        LogUtil.error("ID为" + teacherData.getTeacherId() + "的教师数据修改失败");
        // 回滚事务
        platformTransactionManager.rollback(transactionStatus);
        return result.failed(400, "修改教师数据失败", "ID为" + teacherData.getTeacherId() + "的教师数据修改失败");


    }

    /**
     * 删除指定ID的教师数据
     *
     * @author PrefersMin
     *
     * @param teacherIdList 需要删除的教师ID
     * @return 返回删除结果
     */
    @PostMapping("/deleteTeacherData")
    public ResultVo deleteTeacherData(@RequestBody List<Integer> teacherIdList) {

        // 开始事务
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(new DefaultTransactionDefinition());


        // 记录操作结果
        List<String> record = new ArrayList<>();
        List<String> failedRecord = new ArrayList<>();

        for (int teacherId : teacherIdList) {
            if (!scheduleDataService.queryScheduleDataByTeacherId(teacherId).isEmpty()) {
                LogUtil.error("ID为" + teacherId + "的教师数据删除失败,外键完整性约束检查失败");
                failedRecord.add(teacherDataService.queryTeacherDataByTeacherId(teacherId).getTeacherName() + " 已在课表中任教，不可删除");
            }
        }

        if (failedRecord.size() > 0) {
            failedRecord.forEach(LogUtil::error);
            // 回滚事务
            platformTransactionManager.rollback(transactionStatus);
            return result.failed(400, "教师数据删除失败", failedRecord);
        }

        for (int teacherId : teacherIdList) {
            boolean deleteResult = teacherDataService.deleteTeacherData(teacherId);
            if (!deleteResult) {
                LogUtil.error("ID为" + teacherId + "的教师数据删除失败");
                // 回滚事务
                platformTransactionManager.rollback(transactionStatus);
                return result.failed("删除教师数据失败,操作已回滚");
            }
            record.add("教师数据被删除，教师数据：" + teacherId);
        }

        record.forEach(LogUtil::info);
        LogUtil.info(teacherIdList.size() + "条教师数据被删除");
        // 提交事务
        platformTransactionManager.commit(transactionStatus);
        return result.success(200, "教师数据删除成功", teacherIdList.size() + "条教师数据被删除");

    }

    /**
     * 新增教师数据
     *
     * @author PrefersMin
     *
     * @param teacherData 需要新增的教师数据
     * @return 返回新增结果
     */
    @PostMapping("/addTeacherData")
    public ResultVo addTeacherData(@RequestBody TeacherData teacherData) {

        // 开始事务
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(new DefaultTransactionDefinition());

        boolean addResult = teacherDataService.addTeacherData(teacherData);

        if (addResult) {
            LogUtil.info("新增教师数据，教师数据：" + teacherData);
            // 提交事务
            platformTransactionManager.commit(transactionStatus);
            return result.success(200, "新增教师数据成功", "课程名称为 " + teacherData.getTeacherName() + " 的教师数据新增成功");
        }

        LogUtil.error("新增教师数据失败，课程信息：" + teacherData);
        // 回滚事务
        platformTransactionManager.rollback(transactionStatus);
        return result.failed(400, "新增教师数据失败,操作已回滚", "教师名称为 " + teacherData.getTeacherName() + " 的教师数据新增失败");

    }

}
