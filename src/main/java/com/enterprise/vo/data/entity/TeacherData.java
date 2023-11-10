package com.enterprise.vo.data.entity;

import lombok.Data;

/**
 * 教师对象实体类
 *
 * @author PrefersMin
 * @version 1.3
 */
@Data
public class TeacherData {

    /**
     * 教师ID
     */
    private int teacherId;

    /**
     * 教师名称
     */
    private String teacherName;

    /**
     * 联系方式
     */
    private String teacherPhone;

    /**
     * 所属学院
     */
    private String teacherInstitute;

    /**
     * 是否为专业教师
     */
    private boolean teacherSpecialized;

}
