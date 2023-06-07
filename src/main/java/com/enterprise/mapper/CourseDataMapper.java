package com.enterprise.mapper;

import com.enterprise.entity.CourseData;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CourseDataMapper {

    // 新增课程数据
    @Insert("INSERT INTO course_data(course_name,course_venue,course_specialized) VALUES (#{courseName},#{courseVenue},#{courseSpecialized})")
    boolean addCourseData(CourseData courseData);

    // 删除指定课程ID的课程数据
    @Delete("DELETE FROM course_data WHERE course_id = #{courseId}")
    boolean deleteCourseData(int courseId);

    // 修改指定课程ID的课程数据
    @Update("UPDATE course_data SET course_name = #{courseName}, course_venue = #{courseVenue}, course_specialized = #{courseSpecialized} WHERE course_id = #{courseId}")
    boolean updateCourseData(CourseData courseData);

    // 查询所有课程数据
    @Select("SELECT * FROM course_data")
    List<CourseData> queryAllCourseData();

    // 查询指定课程ID的课程数据
    @Select("SELECT * FROM course_data WHERE course_id=#{courseId}")
    CourseData queryCourseDataByCourseId(int courseId);

}
