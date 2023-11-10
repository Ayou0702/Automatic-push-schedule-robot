package com.enterprise.service.data.entity;

import com.enterprise.vo.data.entity.CourseData;

import java.util.List;

/**
 * 课程数据接口
 *
 * @author PrefersMin
 * @version 1.2
 */
public interface CourseDataService {

    /**
     * 新增课程数据
     *
     * @author PrefersMin
     *
     * @param courseData 需要新增的课程数据
     * @return 返回新增结果
     */
    boolean addCourseData(CourseData courseData);

    /**
     * 删除指定课程ID的课程数据
     *
     * @author PrefersMin
     *
     * @param courseId 需要删除的课程ID
     * @return 返回删除结果
     */
    boolean deleteCourseData(int courseId);

    /**
     * 修改指定课程ID的课程数据
     *
     * @author PrefersMin
     *
     * @param courseData 需要修改的课程数据
     * @return 返回修改结果
     */
    boolean updateCourseData(CourseData courseData);

    /**
     * 查询所有课程数据
     *
     * @author PrefersMin
     *
     * @return 返回查询结果
     */
    List<CourseData> queryAllCourseData();

    /**
     * 查询指定课程ID的课程数据
     *
     * @author PrefersMin
     *
     * @param courseId 需要查询的课程ID
     * @return 返回查询结果
     */
    CourseData queryCourseDataByCourseId(int courseId);

}
