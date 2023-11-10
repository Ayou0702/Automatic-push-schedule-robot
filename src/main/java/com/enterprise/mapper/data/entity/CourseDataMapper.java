package com.enterprise.mapper.data.entity;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.enterprise.vo.data.entity.CourseData;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 课程数据接口
 *
 * @author PrefersMin
 * @version 1.1
 */
@Mapper
@DS("data")
public interface CourseDataMapper {

    /**
     * 新增课程数据
     *
     * @author PrefersMin
     *
     * @param courseData 需要新增的课程数据
     * @return 返回新增结果
     */
    @Insert("INSERT INTO course_data(course_name,course_venue,course_specialized) VALUES (#{courseName},#{courseVenue},#{courseSpecialized})")
    boolean addCourseData(CourseData courseData);

    /**
     * 删除指定课程ID的课程数据
     *
     * @author PrefersMin
     *
     * @param courseId 需要删除的课程ID
     * @return 返回删除结果
     */
    @Delete("DELETE FROM course_data WHERE course_id = #{courseId}")
    boolean deleteCourseData(int courseId);

    /**
     * 修改指定课程ID的课程数据
     *
     * @author PrefersMin
     *
     * @param courseData 需要修改的课程数据
     * @return 返回修改结果
     */
    @Update("UPDATE course_data SET course_name = #{courseName}, course_venue = #{courseVenue}, course_specialized = #{courseSpecialized} WHERE course_id = #{courseId}")
    boolean updateCourseData(CourseData courseData);

    /**
     * 查询所有课程数据
     *
     * @author PrefersMin
     *
     * @return 返回查询结果
     */
    @Select("SELECT * FROM course_data")
    List<CourseData> queryAllCourseData();

    /**
     * 查询指定课程ID的课程数据
     *
     * @author PrefersMin
     *
     * @param courseId 需要查询的课程ID
     * @return 返回查询结果
     */
    @Select("SELECT * FROM course_data WHERE course_id=#{courseId}")
    CourseData queryCourseDataByCourseId(int courseId);

}
