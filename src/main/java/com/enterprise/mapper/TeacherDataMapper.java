package com.enterprise.mapper;

import com.enterprise.entity.TeacherData;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 教师数据接口
 *
 * @author PrefersMin
 * @version 1.1
 */
@Mapper
public interface TeacherDataMapper {

    /**
     * 查询所有教师数据
     *
     * @author PrefersMin
     *
     * @return 返回查询结果
     */
    @Select("SELECT * FROM teacher_data")
    List<TeacherData> queryAllTeacherData();

    /**
     * 查询指定教师ID的教师数据
     *
     * @author PrefersMin
     *
     * @param teacherId 教师ID
     * @return 返回查询结果
     */
    @Select("SELECT * FROM teacher_data WHERE teacher_id=#{teacherId}")
    TeacherData queryTeacherDataByTeacherId(int teacherId);

    /**
     * 更新指定教师数据
     *
     * @author PrefersMin
     *
     * @param teacherData 需要更新的教师数据
     * @return 返回更新结果
     */
    @Update("UPDATE teacher_data SET teacher_name = #{teacherName}, teacher_phone = #{teacherPhone}, teacher_institute = #{teacherInstitute}, teacher_specialized = #{teacherSpecialized} WHERE teacher_id = #{teacherId}")
    boolean updateTeacherData(TeacherData teacherData);

    /**
     * 删除指定教师ID的教师数据
     *
     * @author PrefersMin
     *
     * @param teacherId 教师ID
     * @return 返回删除结果
     */
    @Delete("DELETE FROM teacher_data WHERE teacher_id = #{teacherId}")
    boolean deleteTeacherData(int teacherId);

    /**
     * 新增教师数据
     *
     * @author PrefersMin
     *
     * @param teacherData 需要新增的教师数据
     * @return 返回新增结果
     */
    @Insert("INSERT INTO teacher_data(teacher_name,teacher_phone,teacher_institute,teacher_specialized) VALUES (#{teacherName},#{teacherPhone},#{teacherInstitute},#{teacherSpecialized})")
    boolean addTeacherData(TeacherData teacherData);

}
