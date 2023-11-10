package com.enterprise.mapper.data.view;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.enterprise.vo.pojo.ScheduleInfoDataVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 课表详细数据视图表接口
 *
 * @author PrefersMin
 * @version 1.0
 */
@Mapper
@DS("data")
public interface ScheduleDataInfoMapper {

    /**
     * 查询课表详细数据视图表中所有的数据
     *
     * @return 返回查询结果
     */
    @Select("SELECT * FROM schedule_info_data")
    List<ScheduleInfoDataVo> getAllScheduleInfoData();

}
