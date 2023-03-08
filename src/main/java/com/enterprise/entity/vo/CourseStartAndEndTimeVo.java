package com.enterprise.entity.vo;

import lombok.Data;

/**
 * 课表时间参数
 *
 * @author Iwlthxcl
 * @version 1.0
 * @time 2023/3/8 16:47
 */
@Data
public class CourseStartAndEndTimeVo {

    /**
     * 课程的开始时间和结束时间
     */
    private int startTime, endTime;

}
