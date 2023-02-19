package com.enterprise.mapper;

import com.enterprise.entity.CourseInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * {@code @author:} ayou
 * 负责courseInfo表的数据交互
 */
@Mapper
public interface CourseInfoMapper {

    /**
     * 获取courseInfo表中的所有课程数据
     *
     * @return 返回所有课程数据
     */
    @Select("SELECT * FROM course_info")
    List<CourseInfo> queryCourse ();

}
