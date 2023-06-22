package com.enterprise.entity;


import lombok.Data;

/**
 * 课表对象实体类
 *
 * @author PrefersMin
 * @version 1.1
 */
@Data
public class ScheduleData {

    /**
     * 课程表ID
     */
    private int scheduleId;

    /**
     * 课程ID
     */
    private int courseId;

    /**
     * 教师ID
     */
    private int teacherId;

    /**
     * 上课时间区间
     */
    private String schedulePeriod;
    private String scheduleWeek;
    private String scheduleSection;

}
