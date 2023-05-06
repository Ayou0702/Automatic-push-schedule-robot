package com.enterprise.entity;

import lombok.Data;

import java.sql.Blob;

@Data
public class TeacherData {

    private int teacherId;
    private String teacherName,teacherPhone,teacherInstitute;
    private boolean teacherSpecialized;
    public byte[] teacherAvatar;

}
