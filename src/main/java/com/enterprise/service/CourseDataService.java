package com.enterprise.service;

import com.enterprise.entity.CourseData;

import java.util.List;

public interface CourseDataService {

    List<CourseData> queryAllCourseData();

    List<CourseData> queryAllCourseIdAndCourseName();

    CourseData queryCourseDataByCourseId(int courseId);

    boolean updateCourseData(CourseData courseData);

    boolean deleteCourseData(int courseId);

    boolean addCourseData(CourseData courseData);

}
