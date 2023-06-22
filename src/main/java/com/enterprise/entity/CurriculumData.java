package com.enterprise.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 线性课程表对象实体类
 *
 * @author PrefersMin
 * @version 1.1
 */
@Data
public class CurriculumData {

    /**
     * 推送队列ID
     */
    private int curriculumId;

    /**
     * 详细的上课时间
     */
    private int curriculumPeriod;
    private int curriculumWeek;
    private int curriculumSection;

    /**
     * 课程详情
     */
    private String courseName;
    private String courseVenue;
    private boolean courseSpecialized;
    private int courseId;

    /**
     * 教师i详情
     */
    public String teacherName;
    public String teacherPhone;
    public String teacherInstitute;
    public boolean teacherSpecialized;
    private int teacherId;

}
