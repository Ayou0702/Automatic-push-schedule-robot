package com.enterprise.service.data.view.impl;

import com.enterprise.mapper.data.view.ScheduleDataInfoMapper;
import com.enterprise.service.data.view.ScheduleInfoDataService;
import com.enterprise.vo.pojo.ScheduleInfoDataVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 课表详细数据视图表服务实现
 *
 * @author PrefersMin
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class ScheduleInfoDataServiceImpl implements ScheduleInfoDataService {

    /**
     * 课表详细数据视图表接口
     */
    private final ScheduleDataInfoMapper scheduleDataInfoMapper;

    /**
     * 查询课表详细数据视图表中所有的数据
     *
     * @return 返回查询结果
     */
    @Override
    public List<ScheduleInfoDataVo> getAllScheduleInfoData() {
        return scheduleDataInfoMapper.getAllScheduleInfoData();
    }

}
