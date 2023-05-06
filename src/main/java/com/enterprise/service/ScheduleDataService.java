package com.enterprise.service;

import com.enterprise.entity.ScheduleData;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ScheduleDataService {

    List<ScheduleData> queryAllScheduleData();

    ScheduleData queryScheduleDataByScheduleId(int scheduleId);

    List<ScheduleData> queryScheduleDataByCourseId(int courseId);

    ScheduleData queryScheduleDataByTeacherId(int teacherId);

    boolean updateScheduleData(ScheduleData scheduleData);

    boolean deleteScheduleData(int courseId);

    boolean addScheduleData(ScheduleData scheduleData);

}
