package com.enterprise.service;

import com.enterprise.entity.CourseInfo;
import com.enterprise.mapper.CourseInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 负责courseInfo表业务的接口实现类
 *
 * @author Iwlthxcl
 * @version 1.0
 */
@Service
public class CourseInfoServiceImpl implements CourseInfoService {

    /**
     * 负责courseInfo表的数据交互
     */
    @Resource
    private CourseInfoMapper courseInfoMapper;

    /**
     * 获取所有课程数据
     *
     * @author Iwlthxcl
     *
     * @return 返回课程数据
     */
    @Override
    public List<CourseInfo> queryCourse () {
        return courseInfoMapper.queryCourse();
    }

}
