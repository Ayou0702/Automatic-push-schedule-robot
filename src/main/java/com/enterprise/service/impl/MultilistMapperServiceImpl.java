package com.enterprise.service.impl;

import com.enterprise.entity.vo.ScheduleInfo;
import com.enterprise.mapper.MultilistMapper;
import com.enterprise.service.MultilistMapperService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MultilistMapperServiceImpl implements MultilistMapperService {

    @Resource
    MultilistMapper multilistMapper;

    @Override
    public List<ScheduleInfo> resetCurriculumData() {
        return multilistMapper.resetCurriculumData();
    }

}
