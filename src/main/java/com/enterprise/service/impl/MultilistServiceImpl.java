package com.enterprise.service.impl;

import com.enterprise.entity.vo.ScheduleInfo;
import com.enterprise.mapper.MultilistMapper;
import com.enterprise.service.MultilistService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 多表联动接口实现类
 *
 * @author PrefersMin
 * @version 1.1
 */
@Service
public class MultilistServiceImpl implements MultilistService {

    /**
     * 多表联动接口
     */
    private final MultilistMapper multilistMapper;

    /**
     * 构造器注入Bean
     *
     * @author PrefersMin
     *
     * @param multilistMapper 多表联动接口
     */
    public MultilistServiceImpl(MultilistMapper multilistMapper) {
        this.multilistMapper = multilistMapper;
    }

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
