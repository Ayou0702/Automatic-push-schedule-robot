package com.enterprise.controller;

import com.alibaba.fastjson2.JSONObject;
import com.enterprise.entity.TeacherData;
import com.enterprise.entity.vo.ResultVo;
import com.enterprise.exception.CustomException;
import com.enterprise.service.ScheduleDataService;
import com.enterprise.service.TeacherDataService;
import com.enterprise.util.LogUtil;
import com.enterprise.util.Result;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.util.Objects.isNull;

@RestController
public class TeacherDataController {

    @Resource
    Result result;

    @Resource
    TeacherDataService teacherDataService;

    @Resource
    ScheduleDataService scheduleDataService;

    @Resource
    PlatformTransactionManager platformTransactionManager;

    List<Integer> deleteTeacherData;
    List<TeacherData> diffTeacher;
    List<TeacherData> addTeacherData;

    @GetMapping("/getTeacherData")
    public ResultVo getTeacherData() {

        List<TeacherData> teacherDataList = teacherDataService.queryAllTeacherData();

        if (teacherDataList.isEmpty()) {
            return result.failed("教师数据加载失败");
        }

        return result.success("教师数据加载成功", teacherDataList);

    }

    @PostMapping("/modifyTeacherData")
    public ResultVo modifyTeacherData(@RequestBody String teacherData) {

        // 开始事务
        TransactionStatus transaction = platformTransactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            deleteTeacherData = JSONObject.parseObject(teacherData).getJSONArray("deleteTeacherData").toList(Integer.class);
            diffTeacher = JSONObject.parseObject(teacherData).getJSONArray("diffTeacher").toJavaList(TeacherData.class);
            addTeacherData = JSONObject.parseObject(teacherData).getJSONArray("addTeacherData").toJavaList(TeacherData.class);
        } catch (Exception e) {
            return result.failed(e.getMessage());
        }

        try {

            // 进行数据预检
            previewingData();

            if (!deleteTeacherData.isEmpty()) {
                deleteTeacherData(deleteTeacherData);
            }

            if (!diffTeacher.isEmpty()) {
                updateTeacherData(diffTeacher);
            }

            if (!addTeacherData.isEmpty()) {
                addTeacherData(addTeacherData);
            }

            platformTransactionManager.commit(transaction);
        } catch (CustomException e) {
            platformTransactionManager.rollback(transaction);
            return result.failed(e.getExceptionVo().getErrorCode(), e.getExceptionVo().getErrorMessage(), e.getExceptionVo().getErrorState());

        } catch (Exception e) {
            platformTransactionManager.rollback(transaction);
            return result.failed(e.getMessage());
        }
        return result.success("教师数据修改成功");

    }

    public void updateTeacherData(List<TeacherData> teacherDataList) {
        List<String> record = new ArrayList<>();
        try {
            for (TeacherData teacherData : teacherDataList) {
                boolean updateResult = teacherDataService.updateTeacherData(teacherData);
                if (!updateResult) {
                    LogUtil.error("ID为" + teacherData.getTeacherId() + "的教师信息修改失败");
                    throw new Exception("修改教师信息失败,操作已回滚");
                } else {
                    record.add("教师信息被修改，课程信息：" + teacherData);
                }
            }
            record.forEach(LogUtil::info);
            LogUtil.info(teacherDataList.size() + "条教师数据被修改");
        } catch (Exception e) {
            LogUtil.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }


    public void deleteTeacherData(List<Integer> teacherIdList) {
        List<String> record = new ArrayList<>();
        try {
            for (int teacherId : teacherIdList) {
                boolean deleteResult = teacherDataService.deleteTeacherData(teacherId);
                if (!deleteResult) {
                    LogUtil.error("ID为" + teacherId + "的教师信息删除失败");
                    throw new Exception("删除教师信息失败,操作已回滚");
                } else {
                    record.add("教师信息被删除，教师信息：" + teacherId);
                }
            }
            record.forEach(LogUtil::info);
            LogUtil.info(teacherIdList.size() + "条教师数据被删除");
        } catch (Exception e) {
            LogUtil.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public void addTeacherData(List<TeacherData> teacherDataList) {
        List<String> record = new ArrayList<>();
        try {
            for (TeacherData teacherData : teacherDataList) {
                boolean addResult = teacherDataService.addTeacherData(teacherData);
                if (!addResult) {
                    LogUtil.error("新增教师信息失败，课程信息：" + teacherData);
                    throw new Exception("新增教师信息失败,操作已回滚");
                } else {
                    record.add("新增教师信息，教师信息：" + teacherData);
                }
            }
            record.forEach(LogUtil::info);
            LogUtil.info("新增" + teacherDataList.size() + "条教师信息");
        } catch (Exception e) {
            LogUtil.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public void previewingData() throws CustomException {

        // 创建一个可变字符串类作为容器用以追加字符串到消息序列
        StringBuilder message = new StringBuilder();

        AtomicBoolean deleteTeacherDataPreviewingResult = new AtomicBoolean(false);
        AtomicBoolean diffTeacherPreviewingResult = new AtomicBoolean(false);

        if (!deleteTeacherData.isEmpty()) {
            deleteTeacherData.forEach(courseId -> {
                if (!isNull(scheduleDataService.queryScheduleDataByCourseId(courseId))) {
                    LogUtil.error("ID为" + courseId + "的教师信息删除失败,外键完整性约束检查失败");
                    message.append(teacherDataService.queryTeacherDataByTeacherId(courseId).getTeacherName()).append(" 已在课表中使用，不可删除\n");
                    deleteTeacherDataPreviewingResult.set(true);
                }
            });
        }

        if (!diffTeacher.isEmpty()) {
            diffTeacher.forEach(diffTeacherData -> {
                if (isNull(teacherDataService.queryTeacherDataByTeacherId(diffTeacherData.getTeacherId()))) {
                    LogUtil.error("ID为" + diffTeacherData.getTeacherId() + "的教师信息更新失败,教师信息不存在");
                    message.append(teacherDataService.queryTeacherDataByTeacherId(diffTeacherData.getTeacherId()).getTeacherName()).append(" 的教师信息不存在，请刷新页面\n");
                    diffTeacherPreviewingResult.set(true);
                }
            });
        }

        if (deleteTeacherDataPreviewingResult.get() || diffTeacherPreviewingResult.get()) {
            LogUtil.error("数据预检不通过，操作已回滚");
            throw new CustomException(message.toString(), "数据预检不通过，请检查", 901);
        }


    }

    @PostMapping("/modifyTeacherAvatar")
    public ResultVo modifyTeacherAvatar(@RequestParam("file") MultipartFile file, @RequestParam("teacherId") int teacherId) {

        if (file.isEmpty()) {
            return result.failed("文件上传失败");
        }

        String fileName = file.getOriginalFilename();
        String extension = StringUtils.getFilenameExtension(fileName); // 获取文件扩展名

        if (extension == null) {
            return result.failed("文件不是图片");
        }

        if (!extension.equalsIgnoreCase("png") && !extension.equalsIgnoreCase("jpg") && !extension.equalsIgnoreCase("jpeg")) {
            return result.failed("文件不是图片");
        }

        try {

            byte[] bytes = file.getBytes();

            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);

            boolean modifyResult = teacherDataService.modifyTeacherAvatar(inputStream,teacherId);

            if (!modifyResult) {
                LogUtil.error("ID为" + teacherId + "的教师头像修改失败");
                throw new Exception("修改教师头像失败,操作已回滚");
            } else {
                LogUtil.info("教师头像被修改，教师ID：" + teacherId);
            }

            return result.success("教师头像上传成功");

        } catch (Exception e) {
            LogUtil.error(e.getMessage());
            return result.failed("教师头像上传失败");
        }

    }

    @PostMapping("/deleteTeacherAvatar")
    public ResultVo deleteTeacherAvatar(@RequestBody String teacherId) {

        teacherId = JSONObject.parseObject(teacherId).getString("teacherId");

        try {

            boolean deleteResult = teacherDataService.deleteTeacherAvatar(Integer.parseInt(teacherId));

            if (!deleteResult) {
                LogUtil.error("ID为" + teacherId + "的教师头像删除失败");
                throw new Exception("删除教师头像失败,操作已回滚");
            } else {
                LogUtil.info("教师头像被删除，教师ID：" + teacherId);
            }

            return result.success("教师头像删除成功");

        } catch (Exception e) {
            LogUtil.error(e.getMessage());
            return result.failed("教师头像删除失败");
        }

    }

}
