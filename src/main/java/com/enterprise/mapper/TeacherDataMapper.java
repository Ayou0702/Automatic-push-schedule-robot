package com.enterprise.mapper;

import com.enterprise.entity.TeacherData;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TeacherDataMapper {

    @Select("SELECT * FROM teacher_data")
    List<TeacherData> queryAllTeacherData();

    @Select("SELECT teacher_id,teacher_name,teacher_specialized FROM teacher_data")
    List<TeacherData> queryAllTeacherIdAndTeacherName();

    @Select("SELECT * FROM teacher_data WHERE teacher_id=#{teacherId}")
    TeacherData queryTeacherDataByTeacherId(int teacherId);

    @Update("UPDATE teacher_data SET teacher_name = #{teacherName}, teacher_phone = #{teacherPhone}, teacher_institute = #{teacherInstitute}, teacher_specialized = #{teacherSpecialized} WHERE teacher_id = #{teacherId}")
    boolean updateTeacherData(TeacherData teacherData);

    @Delete("DELETE FROM teacher_data WHERE teacher_id = #{teacherId}")
    boolean deleteTeacherData(int teacherId);

    @Insert("INSERT INTO teacher_data(teacher_name,teacher_phone,teacher_institute,teacher_specialized) VALUES (#{teacherName},#{teacherPhone},#{teacherInstitute},#{teacherSpecialized})")
    boolean addTeacherData(TeacherData teacherData);

}
