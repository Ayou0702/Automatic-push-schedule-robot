package com.enterprise.vo.pojo;

import lombok.Data;

/**
 * 课表详细信息实体类
 *
 * @author PrefersMin
 * @version 1.2
 */
@Data
public class ScheduleInfoDataVo {

    /**
     * 课程表表中的上课时间区间
     */
    private String schedulePeriod;
    private String scheduleWeek;
    private String scheduleSection;

    /**
     * 经过生成后的线性课程表的推送队列ID
     */
    private int curriculumId;

    /**
     * 经过生成后的线性课程表的详细上课时间
     */
    private int curriculumPeriod;
    private int curriculumWeek;
    private int curriculumSection;

    /**
     * 课程详情
     */
    private String courseName;
    private String courseVenue;
    private String courseAvatar;
    private boolean courseSpecialized;
    private int courseId;

    /**
     * 教师详情
     */
    private String teacherName;
    private String teacherPhone;
    private String teacherInstitute;
    private String teacherAvatar;
    private boolean teacherSpecialized;
    private int teacherId;

}
