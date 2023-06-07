package com.enterprise.entity;

import lombok.Data;

/**
 * 教师对象实体类
 *
 * @author PrefersMin
 * @version 1.0
 */
@Data
public class TeacherData {

    public int teacherId;
    public String teacherName,teacherPhone,teacherInstitute;
    public boolean teacherSpecialized;

}
