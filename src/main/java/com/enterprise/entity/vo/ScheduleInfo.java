package com.enterprise.entity.vo;

import lombok.Data;

@Data
public class ScheduleInfo {

    private String schedulePeriod,scheduleWeek,scheduleSection;
    private int curriculumId,curriculumPeriod,curriculumWeek,curriculumSection;

    private String courseName,courseVenue,courseAvatar;
    private boolean courseSpecialized;

    public String teacherName,teacherPhone,teacherInstitute,teacherAvatar;
    public boolean teacherSpecialized;

}
