package com.enterprise.service;

import com.enterprise.entity.TeacherData;

import java.util.List;

public interface TeacherDataService {

    List<TeacherData> queryAllTeacherData();

    List<TeacherData> queryAllTeacherIdAndTeacherName();

    TeacherData queryTeacherDataByTeacherId(int teacherId);

    boolean updateTeacherData(TeacherData teacherData);

    boolean deleteTeacherData(int teacherId);

    boolean addTeacherData(TeacherData teacherData);

}
