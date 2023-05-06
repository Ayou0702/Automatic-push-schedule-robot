package com.enterprise.entity;


import lombok.Data;

@Data
public class ScheduleData {

    private int scheduleId,courseId,teacherId;
    private String schedulePeriod,scheduleWeek,scheduleSection;

}
