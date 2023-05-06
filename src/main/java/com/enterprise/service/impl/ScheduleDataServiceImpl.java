package com.enterprise.service.impl;

import com.enterprise.entity.ScheduleData;
import com.enterprise.mapper.ScheduleDataMapper;
import com.enterprise.service.ScheduleDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ScheduleDataServiceImpl implements ScheduleDataService {

    @Resource
    private ScheduleDataMapper scheduleDataMapper;

    @Override
    public List<ScheduleData> queryAllScheduleData() {
        return scheduleDataMapper.queryAllScheduleData();
    }

    @Override
    public ScheduleData queryScheduleDataByScheduleId(int scheduleId) {
        return scheduleDataMapper.queryScheduleDataByScheduleId(scheduleId);
    }

    @Override
    public List<ScheduleData> queryScheduleDataByCourseId(int courseId) {
        return scheduleDataMapper.queryScheduleDataByCourseId(courseId);
    }

    @Override
    public ScheduleData queryScheduleDataByTeacherId(int teacherId) {
        return scheduleDataMapper.queryScheduleDataByTeacherId(teacherId);
    }

    @Override
    public boolean updateScheduleData(ScheduleData scheduleData) {
        return scheduleDataMapper.updateScheduleData(scheduleData);
    }

    @Override
    public boolean deleteScheduleData(int courseId) {
        return scheduleDataMapper.deleteScheduleData(courseId);
    }

    @Override
    public boolean addScheduleData(ScheduleData scheduleData) {
        return scheduleDataMapper.addScheduleData(scheduleData);
    }

}
