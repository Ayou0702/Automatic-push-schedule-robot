package com.enterprise.service;

import com.enterprise.entity.vo.ScheduleInfo;

import java.util.List;

/**
 * 多表联动接口
 *
 * @author PrefersMin
 * @version 1.1
 */
public interface MultilistService {

    /**
     * 联合课程表、教师表、课表表查询所有的数据
     *
     * @author PrefersMin
     *
     * @return 返回查询结果
     */
    List<ScheduleInfo> resetCurriculumData();

}
