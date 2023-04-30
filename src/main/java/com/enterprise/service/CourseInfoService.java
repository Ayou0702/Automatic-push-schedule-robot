package com.enterprise.service;

import com.enterprise.entity.CourseInfo;

import java.util.List;

/**
 * 负责courseInfo表业务的接口
 *
 * @author PrefersMin
 * @version 1.1
 */
public interface CourseInfoService {

    /**
     * 获取所有课程数据
     *
     * @author PrefersMin
     *
     * @return 返回课程数据
     */
    List<CourseInfo> queryCourse();

    /**
     * 获取courseInfo表中指定ID的课程数据
     *
     * @author PrefersMin
     *
     * @param courseId 课程ID
     * @return 返回课程数据
     */
    CourseInfo queryCourseById(int courseId);

    /**
     * 根据CourseId修改课程信息
     *
     * @author PrefersMin
     *
     * @param courseInfo 需要修改的课程信息
     * @return 返回记录的匹配条数
     */
    boolean updateCourse(CourseInfo courseInfo);

    /**
     * 根据CourseId删除课程信息
     *
     * @author PrefersMin
     *
     * @param courseId 需要删除的课程ID
     * @return 返回受影响的行数，若为 -1 则操作失败
     */
    boolean deleteCourse(int courseId);

    /**
     * 新增课程数据
     *
     * @author PrefersMin
     *
     * @param courseInfo 需要新增的课程信息
     * @return 返回一个boolean代表是否新增成功
     */
    boolean addCourse(CourseInfo courseInfo);
}
