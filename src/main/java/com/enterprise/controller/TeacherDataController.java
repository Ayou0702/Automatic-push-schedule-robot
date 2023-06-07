package com.enterprise.controller;

import com.alibaba.fastjson2.JSONObject;
import com.enterprise.entity.TeacherData;
import com.enterprise.entity.vo.ResultVo;
import com.enterprise.service.ScheduleDataService;
import com.enterprise.service.TeacherDataService;
import com.enterprise.util.LogUtil;
import com.enterprise.util.Result;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@RestController
public class TeacherDataController {

    @Resource
    Result result;

    @Resource
    TeacherDataService teacherDataService;

    @Resource
    ScheduleDataService scheduleDataService;

    List<Integer> deleteTeacherData;
    List<TeacherData> diffTeacher;
    List<TeacherData> addTeacherData;

    @GetMapping("/getTeacherData")
    public ResultVo getTeacherData() {

        List<TeacherData> teacherDataList = teacherDataService.queryAllTeacherData();

        if (teacherDataList == null) {
            return result.failed("教师数据加载失败");
        }

        return result.success("教师数据加载成功", teacherDataList);

    }

    @Transactional
    @PostMapping("/modifyTeacherData")
    public ResultVo modifyTeacherData(@RequestBody String teacherData) {

        try {
            deleteTeacherData = JSONObject.parseObject(teacherData).getJSONArray("deleteTeacherData").toList(Integer.class);
            diffTeacher = JSONObject.parseObject(teacherData).getJSONArray("diffTeacher").toJavaList(TeacherData.class);
            addTeacherData = JSONObject.parseObject(teacherData).getJSONArray("addTeacherData").toJavaList(TeacherData.class);
        } catch (Exception e) {
            return result.failed(e.getMessage());
        }

        try {

            if (!deleteTeacherData.isEmpty()) {
                deleteTeacherData(deleteTeacherData);
            }

            if (!diffTeacher.isEmpty()) {
                updateTeacherData(diffTeacher);
            }

            if (!addTeacherData.isEmpty()) {
                addTeacherData(addTeacherData);
            }

        } catch (Exception e) {
            return result.failed(e.getMessage());
        }
        return result.success("教师数据修改成功");

    }

    @Transactional
    @PostMapping("/updateTeacherData")
    public ResultVo updateTeacherData(@RequestBody List<TeacherData> teacherDataList) {
        List<String> record = new ArrayList<>();
        List<String> failedRecord = new ArrayList<>();
        try {
            for (TeacherData teacherData : teacherDataList) {

                if (isNull(teacherDataService.queryTeacherDataByTeacherId(teacherData.getTeacherId()))) {
                    LogUtil.error("ID为" + teacherData.getTeacherId() + "的教师数据更新失败,教师数据不存在");
                    failedRecord.add(teacherDataService.queryTeacherDataByTeacherId(teacherData.getTeacherId()).getTeacherName()+" 的教师数据不存在，请刷新页面");
                }
                
            }
            if (failedRecord.size() > 0) {
                failedRecord.forEach(LogUtil::error);
                return result.failed(400, "教师数据更新失败", failedRecord);
            } else {
                for (TeacherData teacherData : teacherDataList) {
                    boolean updateResult = teacherDataService.updateTeacherData(teacherData);
                    if (!updateResult) {
                        LogUtil.error("ID为" + teacherData.getTeacherId() + "的教师数据修改失败");
                        throw new Exception("修改教师数据失败,操作已回滚");
                    } else {
                        record.add("教师数据被修改，课程信息：" + teacherData);
                    }
                }
            }
            record.forEach(LogUtil::info);
            LogUtil.info(teacherDataList.size() + "条教师数据被修改");
            return result.success(200,"教师数据修改成功",teacherDataList.size() + "条教师数据被修改");
        } catch (Exception e) {
            LogUtil.error(e.getMessage());
            return  result.failed(e.getMessage());
        }
    }
    
    @Transactional
    @PostMapping("/deleteTeacherData")
    public ResultVo deleteTeacherData(@RequestBody List<Integer> teacherIdList) {
        List<String> record = new ArrayList<>();
        List<String> failedRecord = new ArrayList<>();
        try {
            for (int teacherId : teacherIdList) {

                if (!scheduleDataService.queryScheduleDataByTeacherId(teacherId).isEmpty()) {
                    LogUtil.error("ID为" + teacherId + "的教师数据删除失败,外键完整性约束检查失败");
                    failedRecord.add(teacherDataService.queryTeacherDataByTeacherId(teacherId).getTeacherName()+" 已在课表中任教，不可删除");
                }

            }
            if (failedRecord.size() > 0) {
                failedRecord.forEach(LogUtil::error);
                return result.failed(400, "教师数据删除失败", failedRecord);
            } else {
                for (int teacherId : teacherIdList) {
                    boolean deleteResult = teacherDataService.deleteTeacherData(teacherId);
                    if (!deleteResult) {
                        LogUtil.error("ID为" + teacherId + "的教师数据删除失败");
                        throw new Exception("删除教师数据失败,操作已回滚");
                    } else {
                        record.add("教师数据被删除，教师数据：" + teacherId);
                    }
                }
            }
            record.forEach(LogUtil::info);
            LogUtil.info(teacherIdList.size() + "条教师数据被删除");
            return result.success(200, "教师数据删除成功", teacherIdList.size() + "条教师数据被删除");
        } catch (Exception e) {
            LogUtil.error(e.getMessage());
            return result.failed(e.getMessage());
        }
    }

    @Transactional
    @PostMapping("/addTeacherData")
    public ResultVo addTeacherData(@RequestBody List<TeacherData> teacherDataList) {
        List<String> record = new ArrayList<>();
        try {
            for (TeacherData teacherData : teacherDataList) {
                boolean addResult = teacherDataService.addTeacherData(teacherData);
                if (!addResult) {
                    LogUtil.error("新增教师数据失败，课程信息：" + teacherData);
                    throw new Exception("新增教师数据失败,操作已回滚");
                } else {
                    record.add("新增教师数据，教师数据：" + teacherData);
                }
            }
            record.forEach(LogUtil::info);
            LogUtil.info("新增" + teacherDataList.size() + "条教师数据");
            return result.success(200,"教师数据新增成功","新增" + teacherDataList.size() + "条教师数据");
        } catch (Exception e) {
            LogUtil.error(e.getMessage());
            return result.failed(e.getMessage());
        }
    }

}
