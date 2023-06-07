package com.enterprise.service;

import com.enterprise.entity.TeacherData;

import java.util.List;

/**
 * 教师数据接口
 *
 * @author PrefersMin
 * @version 1.1
 */
public interface TeacherDataService {

    /**
     * 查询所有教师数据
     *
     * @author PrefersMin
     *
     * @return 返回查询结果
     */
    List<TeacherData> queryAllTeacherData();

    /**
     * 查询指定教师ID的教师数据
     *
     * @author PrefersMin
     *
     * @param teacherId 教师ID
     * @return 返回查询结果
     */
    TeacherData queryTeacherDataByTeacherId(int teacherId);

    /**
     * 更新指定教师数据
     *
     * @author PrefersMin
     *
     * @param teacherData 需要更新的教师数据
     * @return 返回更新结果
     */
    boolean updateTeacherData(TeacherData teacherData);

    /**
     * 删除指定教师ID的教师数据
     *
     * @author PrefersMin
     *
     * @param teacherId 教师ID
     * @return 返回删除结果
     */
    boolean deleteTeacherData(int teacherId);

    /**
     * 新增教师数据
     *
     * @author PrefersMin
     *
     * @param teacherData 需要新增的教师数据
     * @return 返回新增结果
     */
    boolean addTeacherData(TeacherData teacherData);

}
