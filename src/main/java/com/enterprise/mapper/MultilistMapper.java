package com.enterprise.mapper;

import com.enterprise.entity.vo.ScheduleInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MultilistMapper {


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
