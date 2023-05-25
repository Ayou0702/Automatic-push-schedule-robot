package com.enterprise.entity;

import lombok.Data;

@Data
public class CurriculumData {

    private int curriculumId,curriculumPeriod,curriculumWeek,curriculumSection;

    private String courseName,courseVenue;
    private boolean courseSpecialized;
    private int courseId;

    public String teacherName,teacherPhone,teacherInstitute;
    public boolean teacherSpecialized;
    private int teacherId;

}
