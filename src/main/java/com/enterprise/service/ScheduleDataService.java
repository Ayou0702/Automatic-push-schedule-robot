package com.enterprise.service;

import com.enterprise.entity.ScheduleData;

import java.util.List;

/**
 * 课表数据接口
 *
 * @author PrefersMin
 * @version 1.1
 */
public interface ScheduleDataService {

    /**
     * 查询所有课表数据
     *
     * @author PrefersMin
     *
     * @return 返回查询结果
     */
    List<ScheduleData> queryAllScheduleData();

    /**
     * 查询指定课表ID的课表数据
     *
     * @author PrefersMin
     *
     * @param scheduleId 课表ID
     * @return 返回查询结果
     */
    ScheduleData queryScheduleDataByScheduleId(int scheduleId);

    /**
     * 查询指定课程ID的课表数据
     *
     * @author PrefersMin
     *
     * @param courseId 课程ID
     * @return 返回查询结果
     */
    List<ScheduleData> queryScheduleDataByCourseId(int courseId);

    /**
     * 查询指定教师ID的教师数据
     *
     * @author PrefersMin
     *
     * @param teacherId 教师ID
     * @return 返回查询结果
     */
    List<ScheduleData> queryScheduleDataByTeacherId(int teacherId);

    /**
     * 更新指定课表数据
     *
     * @author PrefersMin
     *
     * @param scheduleData 需要更新的课表数据
     * @return 返回更新结果
     */
    boolean updateScheduleData(ScheduleData scheduleData);

    /**
     * 删除指定课表ID的课表数据
     *
     * @author PrefersMin
     *
     * @param scheduleId 课表ID
     * @return 返回删除结果
     */
    boolean deleteScheduleData(int scheduleId);

    /**
     * 新增课表数据
     *
     * @author PrefersMin
     *
     * @param scheduleData 需要新增的课表数据
     * @return 返回新增结果
     */
    boolean addScheduleData(ScheduleData scheduleData);

}
