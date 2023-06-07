package com.enterprise.service.impl;

import com.enterprise.entity.vo.ScheduleInfo;
import com.enterprise.mapper.MultilistMapper;
import com.enterprise.service.MultilistMapperService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 多表联动接口实现类
 *
 * @author PrefersMin
 * @version 1.0
 */
@Service
public class MultilistMapperServiceImpl implements MultilistMapperService {

    /**
     * 多表联动接口
     */
    @Resource
    MultilistMapper multilistMapper;

    /**
     * 联合课程表、教师表、课表表查询所有的数据
     *
     * @author PrefersMin
     *
     * @return 返回查询结果
     */
    @Override
    public List<ScheduleInfo> resetCurriculumData() {
        return multilistMapper.resetCurriculumData();
    }

}
