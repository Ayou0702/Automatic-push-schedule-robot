package com.enterprise.entity;

import lombok.Data;

import java.sql.Blob;

@Data
public class TeacherData {

    public int teacherId;
    public String teacherName,teacherPhone,teacherInstitute;
    public boolean teacherSpecialized;
    public byte[] teacherAvatar;

}
