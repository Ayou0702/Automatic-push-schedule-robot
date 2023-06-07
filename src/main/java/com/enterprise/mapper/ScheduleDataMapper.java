package com.enterprise.mapper;

import com.enterprise.entity.ScheduleData;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 课表数据接口
 *
 * @author PrefersMin
 * @version 1.1
 */
@Mapper
public interface ScheduleDataMapper {

    /**
     * 查询所有课表数据
     *
     * @author PrefersMin
     *
     * @return 返回查询结果
     */
    @Select("SELECT * FROM schedule_data")
    List<ScheduleData> queryAllScheduleData();

    /**
     * 查询指定课表ID的课表数据
     *
     * @author PrefersMin
     *
     * @param scheduleId 课表ID
     * @return 返回查询结果
     */
    @Select("SELECT * FROM schedule_data WHERE schedule_id=#{scheduleId}")
    ScheduleData queryScheduleDataByScheduleId(int scheduleId);

    /**
     * 查询指定课程ID的课表数据
     *
     * @author PrefersMin
     *
     * @param courseId 课程ID
     * @return 返回查询结果
     */
    @Select("SELECT * FROM schedule_data WHERE course_id=#{courseId}")
    List<ScheduleData> queryScheduleDataByCourseId(int courseId);

    /**
     * 查询指定教师ID的教师数据
     *
     * @author PrefersMin
     *
     * @param teacherId 教师ID
     * @return 返回查询结果
     */
    @Select("SELECT * FROM schedule_data WHERE teacher_id=#{teacherId}")
    List<ScheduleData> queryScheduleDataByTeacherId(int teacherId);

    /**
     * 更新指定课表数据
     *
     * @author PrefersMin
     *
     * @param scheduleData 需要更新的课表数据
     * @return 返回更新结果
     */
    @Update("UPDATE schedule_data SET course_id = #{courseId}, teacher_id = #{teacherId}, schedule_period = #{schedulePeriod}, schedule_week = #{scheduleWeek}, schedule_section = #{scheduleSection} WHERE schedule_id = #{scheduleId}")
    boolean updateScheduleData(ScheduleData scheduleData);

    /**
     * 删除指定课表ID的课表数据
     *
     * @author PrefersMin
     *
     * @param scheduleId 课表ID
     * @return 返回删除结果
     */
    @Delete("DELETE FROM schedule_data WHERE schedule_id = #{scheduleId}")
    boolean deleteScheduleData(int scheduleId);

    /**
     * 新增课表数据
     *
     * @author PrefersMin
     *
     * @param scheduleData 需要新增的课表数据
     * @return 返回新增结果
     */
    @Insert("INSERT INTO schedule_data(course_id,teacher_id,schedule_period,schedule_week,schedule_section) VALUES (#{courseId},#{teacherId},#{schedulePeriod},#{scheduleWeek},#{scheduleSection})")
    boolean addScheduleData(ScheduleData scheduleData);

}
