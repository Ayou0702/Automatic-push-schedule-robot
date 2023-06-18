package com.enterprise.service.impl;

import com.enterprise.entity.TeacherData;
import com.enterprise.mapper.TeacherDataMapper;
import com.enterprise.service.TeacherDataService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 教师数据接口实现类
 *
 * @author PrefersMin
 * @version 1.2
 */
@Service
public class TeacherDataServiceImpl implements TeacherDataService {

    /**
     * 教师数据接口
     */
    private final TeacherDataMapper teacherDataMapper;

    /**
     * 构造器注入Bean
     *
     * @author PrefersMin
     *
     * @param teacherDataMapper 教师数据接口
     */
    public TeacherDataServiceImpl(TeacherDataMapper teacherDataMapper) {
        this.teacherDataMapper = teacherDataMapper;
    }

    /**
     * 查询所有教师数据
     *
     * @author PrefersMin
     *
     * @return 返回查询结果
     */
    @Override
    public List<TeacherData> queryAllTeacherData() {
        return teacherDataMapper.queryAllTeacherData();
    }

    /**
     * 查询指定教师ID的教师数据
     *
     * @author PrefersMin
     *
     * @param teacherId 教师ID
     * @return 返回查询结果
     */
    @Override
    public TeacherData queryTeacherDataByTeacherId(int teacherId) {
        return teacherDataMapper.queryTeacherDataByTeacherId(teacherId);
    }

    /**
     * 更新指定教师数据
     *
     * @author PrefersMin
     *
     * @param teacherData 需要更新的教师数据
     * @return 返回更新结果
     */
    @Override
    public boolean updateTeacherData(TeacherData teacherData) {
        return teacherDataMapper.updateTeacherData(teacherData);
    }

    /**
     * 删除指定教师ID的教师数据
     *
     * @author PrefersMin
     *
     * @param teacherId 教师ID
     * @return 返回删除结果
     */
    @Override
    public boolean deleteTeacherData(int teacherId) {
        return teacherDataMapper.deleteTeacherData(teacherId);
    }

    /**
     * 新增教师数据
     *
     * @author PrefersMin
     *
     * @param teacherData 需要新增的教师数据
     * @return 返回新增结果
     */
    @Override
    public boolean addTeacherData(TeacherData teacherData) {
        return teacherDataMapper.addTeacherData(teacherData);
    }

}
