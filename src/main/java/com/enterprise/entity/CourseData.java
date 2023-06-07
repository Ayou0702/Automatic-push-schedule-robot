package com.enterprise.entity;

import lombok.Data;

/**
 * 课程对象实体类
 *
 * @author PrefersMin
 * @version 1.0
 */
@Data
public class CourseData {

    private int courseId;
    private String courseName,courseVenue;
    private boolean courseSpecialized;

}
