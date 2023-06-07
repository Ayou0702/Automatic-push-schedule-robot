package com.enterprise.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class CurriculumData {

    @TableId(type = IdType.AUTO)
    private Integer curriculumId;

    private int curriculumPeriod,curriculumWeek,curriculumSection;

    private String courseName,courseVenue;
    private boolean courseSpecialized;
    private int courseId;

    public String teacherName,teacherPhone,teacherInstitute;
    public boolean teacherSpecialized;
    private int teacherId;

}
