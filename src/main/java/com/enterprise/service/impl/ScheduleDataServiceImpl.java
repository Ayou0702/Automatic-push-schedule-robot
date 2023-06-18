package com.enterprise.service.impl;

import com.enterprise.entity.ScheduleData;
import com.enterprise.mapper.ScheduleDataMapper;
import com.enterprise.service.ScheduleDataService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 课表数据接口实现类
 *
 * @author PrefersMin
 * @version 1.2
 */
@Service
public class ScheduleDataServiceImpl implements ScheduleDataService {

    /**
     * 课表数据接口
     */
    private final ScheduleDataMapper scheduleDataMapper;

    /**
     * 构造器注入Bean
     *
     * @author PrefersMin
     *
     * @param scheduleDataMapper 课表数据接口
     */
    public ScheduleDataServiceImpl(ScheduleDataMapper scheduleDataMapper) {
        this.scheduleDataMapper = scheduleDataMapper;
    }

    /**
     * 查询所有课表数据
     *
     * @author PrefersMin
     *
     * @return 返回查询结果
     */
    @Override
    public List<ScheduleData> queryAllScheduleData() {
        return scheduleDataMapper.queryAllScheduleData();
    }

    /**
     * 查询指定课表ID的课表数据
     *
     * @author PrefersMin
     *
     * @param scheduleId 课表ID
     * @return 返回查询结果
     */
    @Override
    public ScheduleData queryScheduleDataByScheduleId(int scheduleId) {
        return scheduleDataMapper.queryScheduleDataByScheduleId(scheduleId);
    }

    /**
     * 查询指定课程ID的课表数据
     *
     * @author PrefersMin
     *
     * @param courseId 课程ID
     * @return 返回查询结果
     */
    @Override
    public List<ScheduleData> queryScheduleDataByCourseId(int courseId) {
        return scheduleDataMapper.queryScheduleDataByCourseId(courseId);
    }

    /**
     * 查询指定教师ID的教师数据
     *
     * @author PrefersMin
     *
     * @param teacherId 教师ID
     * @return 返回查询结果
     */
    @Override
    public List<ScheduleData> queryScheduleDataByTeacherId(int teacherId) {
        return scheduleDataMapper.queryScheduleDataByTeacherId(teacherId);
    }

    /**
     * 更新指定课表数据
     *
     * @author PrefersMin
     *
     * @param scheduleData 需要更新的课表数据
     * @return 返回更新结果
     */
    @Override
    public boolean updateScheduleData(ScheduleData scheduleData) {
        return scheduleDataMapper.updateScheduleData(scheduleData);
    }

    /**
     * 删除指定课表ID的课表数据
     *
     * @author PrefersMin
     *
     * @param scheduleId 课表ID
     * @return 返回删除结果
     */
    @Override
    public boolean deleteScheduleData(int scheduleId) {
        return scheduleDataMapper.deleteScheduleData(scheduleId);
    }

    /**
     * 新增课表数据
     *
     * @author PrefersMin
     *
     * @param scheduleData 需要新增的课表数据
     * @return 返回新增结果
     */
    @Override
    public boolean addScheduleData(ScheduleData scheduleData) {
        return scheduleDataMapper.addScheduleData(scheduleData);
    }

}
