package com.enterprise.entity;

import lombok.Data;

@Data
public class CurriculumData {

    private int curriculumId,curriculumPeriod,curriculumWeek,curriculumSection;

    private String courseName,courseVenue,courseAvatar;
    private boolean courseSpecialized;

    public String teacherName,teacherPhone,teacherInstitute,teacherAvatar;
    public boolean teacherSpecialized;

}
