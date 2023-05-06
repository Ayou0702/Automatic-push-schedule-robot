package com.enterprise.mapper;

import com.enterprise.entity.CourseData;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CourseDataMapper {

    @Select("SELECT * FROM course_data")
    List<CourseData> queryAllCourseData();

    @Select("SELECT * FROM course_data WHERE course_id=#{courseId}")
    CourseData queryCourseDataByCourseId(int courseId);

    @Update("UPDATE course_data SET course_name = #{courseName}, course_venue = #{courseVenue}, course_specialized = #{courseSpecialized} WHERE course_id = #{courseId}")
    boolean updateCourseData(CourseData courseData);

    @Delete("DELETE FROM course_data WHERE course_id = #{courseId}")
    boolean deleteCourseData(int courseId);

    @Insert("INSERT INTO course_info(course_name,course_venue,course_specialized) VALUES (#{courseName},#{courseVenue},#{courseSpecialized})")
    boolean addCourseData(CourseData courseData);

}
