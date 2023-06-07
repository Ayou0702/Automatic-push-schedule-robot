package com.enterprise.entity;

import lombok.Data;

@Data
public class TeacherData {

    public int teacherId;
    public String teacherName,teacherPhone,teacherInstitute;
    public boolean teacherSpecialized;

}
