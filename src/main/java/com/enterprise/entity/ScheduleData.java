package com.enterprise.entity;


import lombok.Data;

/**
 * 课表对象实体类
 *
 * @author PrefersMin
 * @version 1.0
 */
@Data
public class ScheduleData {

    private int scheduleId,courseId,teacherId;
    private String schedulePeriod,scheduleWeek,scheduleSection;

}
