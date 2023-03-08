package com.enterprise.entity;

import lombok.Data;

/**
 * courseInfo表的实体类
 *
 * @author Iwlthxcl
 * @version 1.0
 * @time 2023/3/8 16:47
 */
@Data
public class CourseInfo {

    /**
     * courseInfo表的字段,转换驼峰命名
     */
    private Integer courseId, courseSpecialized;
    private String courseName, courseVenue, coursePeriod, courseWeek, courseSection;

}
