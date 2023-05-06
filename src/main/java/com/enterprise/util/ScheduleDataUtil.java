package com.enterprise.util;

import com.enterprise.entity.ScheduleData;
import com.enterprise.service.ScheduleDataService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class ScheduleDataUtil {

    @Resource
    private ScheduleDataService scheduleDataService;

    public List<ScheduleData> queryAllScheduleData(){
        return scheduleDataService.queryAllScheduleData();
    }

}
