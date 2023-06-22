package com.enterprise.entity;

import lombok.Data;

/**
 * 课程对象实体类
 *
 * @author PrefersMin
 * @version 1.1
 */
@Data
public class CourseData {

    /**
     * 课程ID
     */
    private int courseId;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 上课地点
     */
    private String courseVenue;

    /**
     * 是否为专业课
     */
    private boolean courseSpecialized;

}
