package com.enterprise.service;

import com.enterprise.entity.CourseInfo;

import java.util.List;

/**
 * 负责courseInfo表业务的接口
 */
public interface CourseInfoService {

    /**
     * 获取所有课程数据
     *
     * @return 返回课程数据
     */
    List<CourseInfo> queryCourse ();

}
