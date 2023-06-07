package com.enterprise.service;

import com.enterprise.entity.vo.ScheduleInfo;

import java.util.List;

/**
 * 多表联动接口
 *
 * @author PrefersMin
 * @version 1.0
 */
public interface MultilistMapperService {

    List<ScheduleInfo> resetCurriculumData();

}
