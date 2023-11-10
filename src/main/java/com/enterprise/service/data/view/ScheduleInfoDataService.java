package com.enterprise.service.data.view;

import com.enterprise.vo.pojo.ScheduleInfoDataVo;

import java.util.List;

/**
 * 课表详细数据视图表服务
 *
 * @author PrefersMin
 * @version 1.0
 */
public interface ScheduleInfoDataService {

    /**
     * 查询课表详细数据视图表中所有的数据
     *
     * @return 返回查询结果
     */
    List<ScheduleInfoDataVo> getAllScheduleInfoData();

}
