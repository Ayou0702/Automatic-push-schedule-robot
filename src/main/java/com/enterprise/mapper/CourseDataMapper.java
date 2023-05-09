package com.enterprise.mapper;

import com.enterprise.entity.CourseData;
import org.apache.ibatis.annotations.*;

import java.io.ByteArrayInputStream;
import java.util.List;

@Mapper
public interface CourseDataMapper {

    @Select("SELECT * FROM course_data")
    List<CourseData> queryAllCourseData();

    @Select("SELECT course_id,course_name,course_specialized,course_avatar FROM course_data")
    List<CourseData> queryAllCourseIdAndCourseName();

    @Select("SELECT * FROM course_data WHERE course_id=#{courseId}")
    CourseData queryCourseDataByCourseId(int courseId);

    @Select("SELECT course_avatar FROM course_data WHERE course_id=#{courseId}")
    CourseData queryCourseAvatarByTeacherId(int courseId);

    @Update("UPDATE course_data SET course_name = #{courseName}, course_venue = #{courseVenue}, course_specialized = #{courseSpecialized} WHERE course_id = #{courseId}")
    boolean updateCourseData(CourseData courseData);

    @Update("UPDATE course_data SET course_avatar = #{courseAvatar} WHERE course_id = #{courseId}")
    boolean modifyCourseAvatar(ByteArrayInputStream courseAvatar, int courseId);

    @Update("UPDATE course_data SET course_avatar = NULL WHERE course_id = #{courseId}")
    boolean deleteCourseAvatar(int courseId);

    @Delete("DELETE FROM course_data WHERE course_id = #{courseId}")
    boolean deleteCourseData(int courseId);

    @Insert("INSERT INTO course_data(course_name,course_venue,course_specialized) VALUES (#{courseName},#{courseVenue},#{courseSpecialized})")
    boolean addCourseData(CourseData courseData);

}
