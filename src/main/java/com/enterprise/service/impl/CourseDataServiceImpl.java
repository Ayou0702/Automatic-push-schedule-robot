package com.enterprise.service.impl;

import com.enterprise.entity.CourseData;
import com.enterprise.mapper.CourseDataMapper;
import com.enterprise.service.CourseDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.util.List;

@Service
public class CourseDataServiceImpl implements CourseDataService {

    @Resource
    private CourseDataMapper courseDataMapper;

    @Override
    public List<CourseData> queryAllCourseData() {
        return courseDataMapper.queryAllCourseData();
    }

    @Override
    public List<CourseData> queryAllCourseIdAndCourseName() {
        return courseDataMapper.queryAllCourseIdAndCourseName();
    }

    @Override
    public CourseData queryCourseDataByCourseId(int courseId) {
        return courseDataMapper.queryCourseDataByCourseId(courseId);
    }

    @Override
    public CourseData queryCourseAvatarByTeacherId(int courseId) {
        return courseDataMapper.queryCourseAvatarByTeacherId(courseId);
    }

    @Override
    public boolean updateCourseData(CourseData courseData) {
        return courseDataMapper.updateCourseData(courseData);
    }

    @Override
    public boolean modifyCourseAvatar(ByteArrayInputStream courseAvatar, int courseId) {
        return courseDataMapper.modifyCourseAvatar(courseAvatar, courseId);
    }

    @Override
    public boolean deleteCourseAvatar(int courseId) {
        return courseDataMapper.deleteCourseAvatar(courseId);
    }

    @Override
    public boolean deleteCourseData(int courseId) {
        return courseDataMapper.deleteCourseData(courseId);
    }

    @Override
    public boolean addCourseData(CourseData courseData) {
        return courseDataMapper.addCourseData(courseData);
    }

}
