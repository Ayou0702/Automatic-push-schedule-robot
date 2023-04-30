package com.enterprise.mapper;

import com.enterprise.entity.CourseInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 负责courseInfo表的数据交互
 *
 * @author PrefersMin
 * @version 1.1
 */
@Mapper
public interface CourseInfoMapper {

    /**
     * 获取courseInfo表中的所有课程数据
     *
     * @author PrefersMin
     *
     * @return 返回所有课程数据
     */
    @Select("SELECT * FROM course_info")
    List<CourseInfo> queryCourse();

    /**
     * 获取courseInfo表中指定ID的课程数据
     *
     * @author PrefersMin
     *
     * @param courseId 课程ID
     * @return 返回课程数据
     */
    @Select("SELECT * FROM course_info WHERE course_id = #{courseId}")
    CourseInfo queryCourseById(int courseId);

    /**
     * 根据CourseId修改课程信息
     *
     * @author PrefersMin
     *
     * @param courseInfo 需要修改的课程信息
     * @return 返回记录的匹配条数
     */
    @Update("UPDATE course_info SET course_name = #{courseName}, course_venue = #{courseVenue}, course_period = #{coursePeriod} , course_week = #{courseWeek}, course_section = #{courseSection} , course_specialized = #{courseSpecialized} WHERE course_id = #{courseId}")
    boolean updateCourse(CourseInfo courseInfo);

    /**
     * 根据CourseId删除课程信息
     *
     * @author PrefersMin
     *
     * @param courseId 需要删除的课程ID
     * @return 返回受影响的行数，若为 -1 则操作失败
     */
    @Delete("DELETE FROM course_info WHERE course_id = #{courseId}")
    boolean deleteCourse(int courseId);

    /**
     * 新增课程数据
     *
     * @author PrefersMin
     *
     * @param courseInfo 需要新增的课程信息
     * @return 返回一个boolean代表是否新增成功
     */
    @Insert("INSERT INTO course_info(course_id,course_name,course_venue,course_period,course_week,course_section,course_specialized) VALUES (#{courseId},#{courseName},#{courseVenue},#{coursePeriod},#{courseWeek},#{courseSection},#{courseSpecialized})")
    boolean addCourse(CourseInfo courseInfo);
}
