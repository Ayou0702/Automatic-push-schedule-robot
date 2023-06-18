package com.enterprise.entity;

import lombok.Data;

/**
 * 教师对象实体类
 *
 * @author PrefersMin
 * @version 1.1
 */
@Data
public class TeacherData {

    private int teacherId;
    private String teacherName,teacherPhone,teacherInstitute;
    private boolean teacherSpecialized;

}
