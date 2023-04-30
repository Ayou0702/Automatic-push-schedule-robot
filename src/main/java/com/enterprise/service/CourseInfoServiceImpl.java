package com.enterprise.service;

import com.enterprise.entity.CourseInfo;
import com.enterprise.mapper.CourseInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 负责courseInfo表业务的接口实现类
 *
 * @author PrefersMin
 * @version 1.1
 */
@Service
public class CourseInfoServiceImpl implements CourseInfoService {

    /**
     * 负责courseInfo表的数据交互
     */
    @Resource
    private CourseInfoMapper courseInfoMapper;

    /**
     * 获取所有课程数据
     *
     * @author PrefersMin
     *
     * @return 返回课程数据
     */
    @Override
    public List<CourseInfo> queryCourse() {
        return courseInfoMapper.queryCourse();
    }

    /**
     * 获取courseInfo表中指定ID的课程数据
     *
     * @author PrefersMin
     *
     * @param courseId 课程ID
     * @return 返回课程数据
     */
    @Override
    public CourseInfo queryCourseById(int courseId) {
        return courseInfoMapper.queryCourseById(courseId);
    }

    /**
     * 根据CourseId修改课程信息
     *
     * @author PrefersMin
     *
     * @param courseInfo 需要修改的课程信息
     * @return 返回记录的匹配条数
     */
    @Override
    public boolean updateCourse(CourseInfo courseInfo) {
        return courseInfoMapper.updateCourse(courseInfo);
    }

    /**
     * 根据CourseId删除课程信息
     *
     * @author PrefersMin
     *
     * @param courseId 需要删除的课程ID
     * @return 返回受影响的行数，若为 -1 则操作失败
     */
    @Override
    public boolean deleteCourse(int courseId) {
        return courseInfoMapper.deleteCourse(courseId);
    }

    /**
     * 新增课程数据
     *
     * @author PrefersMin
     *
     * @param courseInfo 需要新增的课程信息
     * @return 返回一个boolean代表是否新增成功
     */
    @Override
    public boolean addCourse(CourseInfo courseInfo) {
        return courseInfoMapper.addCourse(courseInfo);
    }
}
