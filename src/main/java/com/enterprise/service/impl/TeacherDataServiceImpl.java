package com.enterprise.service.impl;

import com.enterprise.entity.TeacherData;
import com.enterprise.mapper.TeacherDataMapper;
import com.enterprise.service.TeacherDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TeacherDataServiceImpl implements TeacherDataService {

    @Resource
    TeacherDataMapper teacherDataMapper;

    @Override
    public List<TeacherData> queryAllTeacherData() {
        return teacherDataMapper.queryAllTeacherData();
    }

    @Override
    public TeacherData queryTeacherDataByTeacherId(int teacherId) {
        return teacherDataMapper.queryTeacherDataByTeacherId(teacherId);
    }

    @Override
    public boolean updateTeacherData(TeacherData teacherData) {
        return teacherDataMapper.updateTeacherData(teacherData);
    }

    @Override
    public boolean deleteTeacherData(int teacherId) {
        return teacherDataMapper.deleteTeacherData(teacherId);
    }

    @Override
    public boolean addTeacherData(TeacherData teacherData) {
        return teacherDataMapper.addTeacherData(teacherData);
    }

}
