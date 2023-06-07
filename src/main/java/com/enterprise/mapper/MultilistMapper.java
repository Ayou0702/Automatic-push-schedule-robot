package com.enterprise.mapper;

import com.enterprise.entity.vo.ScheduleInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 多表联动接口
 *
 * @author PrefersMin
 * @version 1.1
 */
@Mapper
public interface MultilistMapper {

    /**
     * 联合课程表、教师表、课表表查询所有的数据
     *
     * @author PrefersMin
     *
     * @return 返回查询结果
     */
    @Select("SELECT\n" +
            "\tcourse_data.course_name, \n" +
            "\tcourse_data.course_venue, \n" +
            "\tcourse_data.course_specialized, \n" +
            "\tschedule_data.schedule_id, \n" +
            "\tschedule_data.schedule_period, \n" +
            "\tschedule_data.schedule_week, \n" +
            "\tschedule_data.schedule_section, \n" +
            "\tteacher_data.teacher_name, \n" +
            "\tteacher_data.teacher_phone, \n" +
            "\tteacher_data.teacher_institute, \n" +
            "\tteacher_data.teacher_specialized, \n" +
            "\tschedule_data.course_id, \n" +
            "\tschedule_data.teacher_id\n" +
            "FROM\n" +
            "\tschedule_data\n" +
            "\tINNER JOIN\n" +
            "\tcourse_data\n" +
            "\tON \n" +
            "\t\tschedule_data.course_id = course_data.course_id\n" +
            "\tINNER JOIN\n" +
            "\tteacher_data\n" +
            "\tON \n" +
            "\t\tschedule_data.teacher_id = teacher_data.teacher_id")
    List<ScheduleInfo> resetCurriculumData();

}
