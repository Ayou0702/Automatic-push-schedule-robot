package com.enterprise.entity.vo;

import lombok.Data;

/**
 * 课表详细信息实体类
 *
 * @author PrefersMin
 * @version 1.0
 */
@Data
public class ScheduleInfo {

    private String schedulePeriod,scheduleWeek,scheduleSection;
    private int curriculumId,curriculumPeriod,curriculumWeek,curriculumSection;

    private String courseName,courseVenue,courseAvatar;
    private boolean courseSpecialized;
    private int courseId;

    public String teacherName,teacherPhone,teacherInstitute,teacherAvatar;
    public boolean teacherSpecialized;
    private int teacherId;

}
