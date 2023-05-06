package com.enterprise.mapper;

import com.enterprise.entity.CourseData;
import com.enterprise.entity.ScheduleData;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ScheduleDataMapper {

    @Select("SELECT * FROM schedule_data")
    List<ScheduleData> queryAllScheduleData();

    @Select("SELECT * FROM schedule_data WHERE schedule_id=#{scheduleId}")
    ScheduleData queryScheduleDataByScheduleId(int scheduleId);

    @Select("SELECT * FROM schedule_data WHERE course_id=#{courseId}")
    List<ScheduleData> queryScheduleDataByCourseId(int courseId);

    @Select("SELECT * FROM schedule_data WHERE teacher_id=#{teacherId}")
    ScheduleData queryScheduleDataByTeacherId(int teacherId);

    @Update("UPDATE schedule_data SET course_id = #{courseId}, teacher_id = #{teacherId}, schedule_period = #{schedulePeriod}, schedule_week = #{scheduleWeek}, schedule_section = #{scheduleSection} WHERE schedule_id = #{scheduleId}")
    boolean updateScheduleData(ScheduleData scheduleData);

    @Delete("DELETE FROM schedule_data WHERE schedule_id = #{scheduleId}")
    boolean deleteScheduleData(int courseId);

    @Insert("INSERT INTO schedule_data(course_id,teacher_id,schedule_period,schedule_week,schedule_section) VALUES (#{courseId},#{teacherId},#{schedulePeriod},#{scheduleWeek},#{scheduleSection})")
    boolean addScheduleData(ScheduleData scheduleData);

}
