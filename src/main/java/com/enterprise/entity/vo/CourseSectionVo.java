package com.enterprise.entity.vo;

import com.enterprise.entity.CourseInfo;
import lombok.Data;

/**
 * 课程实体类
 */
@Data
public class CourseSectionVo {

    /**
     * 五大节课程
     */
    private CourseInfo first, second, thirdly, fourthly, fifth;

}
