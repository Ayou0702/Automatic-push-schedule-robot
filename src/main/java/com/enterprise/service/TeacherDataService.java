package com.enterprise.service;

import com.enterprise.entity.TeacherData;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.util.List;

public interface TeacherDataService {

    List<TeacherData> queryAllTeacherData();

    List<TeacherData> queryAllTeacherIdAndTeacherName();

    TeacherData queryTeacherDataByTeacherId(int teacherId);

    TeacherData queryTeacherAvatarByTeacherId(int teacherId);

    boolean updateTeacherData(TeacherData teacherData);

    boolean modifyTeacherAvatar(ByteArrayInputStream teacherAvatar, int teacherId);

    boolean deleteTeacherAvatar(int teacherId);

    boolean deleteTeacherData(int teacherId);

    boolean addTeacherData(TeacherData teacherData);

}
