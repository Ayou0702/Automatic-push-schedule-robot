package com.enterprise.service;

import com.enterprise.entity.CourseInfo;

import java.util.List;

/**
 * 负责courseInfo表业务的接口
 *
 * @author Iwlthxcl
 * @version 1.0
 * @time 2023/3/8 16:50
 */
public interface CourseInfoService {

    /**
     * 获取所有课程数据
     *
     * @return 返回课程数据
     *
     * @author Iwlthxcl
     * @time 2023/3/8 16:50
     */
    List<CourseInfo> queryCourse ();

}
