package com.enterprise.service.impl;

import com.enterprise.entity.CourseData;
import com.enterprise.mapper.CourseDataMapper;
import com.enterprise.service.CourseDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 课程数据接口实现类
 *
 * @author PrefersMin
 * @version 1.3
 */
@Service
@RequiredArgsConstructor
public class CourseDataServiceImpl implements CourseDataService {

    /**
     * 课程数据接口
     */
    private final CourseDataMapper courseDataMapper;

    /**
     * 新增课程数据
     *
     * @author PrefersMin
     *
     * @param courseData 需要新增的课程数据
     * @return 返回新增结果
     */
    @Override
    public boolean addCourseData(CourseData courseData) {
        return courseDataMapper.addCourseData(courseData);
    }

    /**
     * 删除指定课程ID的课程数据
     *
     * @author PrefersMin
     *
     * @param courseId 需要删除的课程ID
     * @return 返回删除结果
     */
    @Override
    public boolean deleteCourseData(int courseId) {
        return courseDataMapper.deleteCourseData(courseId);
    }

    /**
     * 修改指定课程ID的课程数据
     *
     * @author PrefersMin
     *
     * @param courseData 需要修改的课程数据
     * @return 返回修改结果
     */
    @Override
    public boolean updateCourseData(CourseData courseData) {
        return courseDataMapper.updateCourseData(courseData);
    }

    /**
     * 查询所有课程数据
     *
     * @author PrefersMin
     *
     * @return 返回查询结果
     */
    @Override
    public List<CourseData> queryAllCourseData() {
        return courseDataMapper.queryAllCourseData();
    }

    /**
     * 查询指定课程ID的课程数据
     *
     * @author PrefersMin
     *
     * @param courseId 需要查询的课程ID
     * @return 返回查询结果
     */
    @Override
    public CourseData queryCourseDataByCourseId(int courseId) {
        return courseDataMapper.queryCourseDataByCourseId(courseId);
    }

}
