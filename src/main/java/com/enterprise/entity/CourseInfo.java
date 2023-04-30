package com.enterprise.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * courseInfo表的实体类
 *
 * @author PrefersMin
 * @version 1.1
 */
@Data
public class CourseInfo {

    /**
     * courseInfo表的字段,转换驼峰命名
     */
    private boolean courseSpecialized;
    private Integer courseId;
    private String courseName, courseVenue, coursePeriod, courseWeek, courseSection;

}
