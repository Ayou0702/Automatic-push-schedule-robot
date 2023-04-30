package com.enterprise.controller;

import com.alibaba.fastjson2.JSONObject;
import com.enterprise.entity.CourseInfo;
import com.enterprise.entity.vo.ResultVo;
import com.enterprise.service.CourseInfoService;
import com.enterprise.util.LogUtil;
import com.enterprise.util.Result;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 负责前端页面的课表数据
 *
 * @author PrefersMin
 * @version 1.1
 */
@RestController
public class CourseInfoDataController {

  @Resource
  Result result;

  /**
   * courseInfo的接口，用于读取查询课程数据
   */
  @Resource
  private CourseInfoService courseInfoService;

  @Resource
  private PlatformTransactionManager transactionManager;


  /**
   * 获取全部的课程数据
   *
   * @author PrefersMin
   *
   * @return 所有课程数据
   */
  @GetMapping("/getCourseInfo")
  public ResultVo getCourseInfo() {

    List<CourseInfo> courseInfoList = courseInfoService.queryCourse();

    if (courseInfoList.isEmpty()) {
      return result.failed("课表数据加载失败");
    }

    return result.success("课表数据加载成功", courseInfoList);

  }

  @PostMapping("/modifyCourse")
  public ResultVo modifyCourse(@RequestBody String courseInfos) {

    // 开始事务
    TransactionStatus transaction = transactionManager.getTransaction(new DefaultTransactionDefinition());

    List<Integer> deleteCourseInfoData;
    List<CourseInfo> diffCourse;
    List<CourseInfo> addCourseInfoData;

    try {
      deleteCourseInfoData = JSONObject.parseObject(courseInfos).getJSONArray("deleteCourseInfoData").toList(Integer.class);
      diffCourse = JSONObject.parseObject(courseInfos).getJSONArray("diffCourse").toJavaList(CourseInfo.class);
      addCourseInfoData = JSONObject.parseObject(courseInfos).getJSONArray("addCourseInfoData").toJavaList(CourseInfo.class);
    } catch (Exception e) {
      return result.failed(e.getMessage());
    }

    try {

      if (!deleteCourseInfoData.isEmpty()) {
        deleteCourse(deleteCourseInfoData);
      }

      if (!diffCourse.isEmpty()) {
        updateCourse(diffCourse);
      }

      if (!addCourseInfoData.isEmpty()) {
        addCourse(addCourseInfoData);
      }

      transactionManager.commit(transaction);
    } catch (Exception e) {
      transactionManager.rollback(transaction);
      return result.failed(e.getMessage());
    }
    return result.success("课程数据修改成功");

  }

  public void updateCourse(List<CourseInfo> courseInfos) {
    List<String> record = new ArrayList<>();
    try {
      for (CourseInfo courseInfo : courseInfos) {
        boolean updateResult = courseInfoService.updateCourse(courseInfo);
        if (!updateResult) {
          LogUtil.error("ID为" + courseInfo.getCourseId() + "的课程信息修改失败");
          throw new Exception("修改课程失败,操作已回滚");
        } else {
          record.add("课程信息被修改，课程信息：" + courseInfo);
        }
      }
      record.forEach(LogUtil::info);
      LogUtil.info(courseInfos.size() + "条课程数据被修改");
    } catch (Exception e) {
      LogUtil.error(e.getMessage());
      throw new RuntimeException(e.getMessage());
    }
  }


  public void deleteCourse(List<Integer> courseIds) {
    List<String> record = new ArrayList<>();
    try {
      for (int courseId : courseIds) {
        boolean deleteResult = courseInfoService.deleteCourse(courseId);
        if (!deleteResult) {
          LogUtil.error("ID为" + courseId + "的课程信息删除失败");
          throw new Exception("删除课程失败,操作已回滚");
        } else {
          record.add("课程信息被删除，课程信息：" + courseId);
        }
      }
      record.forEach(LogUtil::info);
      LogUtil.info(courseIds.size() + "条课程数据被删除");
    } catch (Exception e) {
      LogUtil.error(e.getMessage());
      throw new RuntimeException(e.getMessage());
    }
  }

  public void addCourse(List<CourseInfo> courseInfos) {
    List<String> record = new ArrayList<>();
    try {
      for (CourseInfo courseInfo : courseInfos) {
        boolean addResult = courseInfoService.addCourse(courseInfo);
        if (!addResult) {
          LogUtil.error("新增课程信息失败，课程信息：" + courseInfo);
          throw new Exception("新增课程失败,操作已回滚");
        } else {
          record.add("新增课程信息，课程信息：" + courseInfo);
        }
      }
      record.forEach(LogUtil::info);
      LogUtil.info("新增" + courseInfos.size() + "条课程信息");
    } catch (Exception e) {
      LogUtil.error(e.getMessage());
      throw new RuntimeException(e.getMessage());
    }
  }

}
