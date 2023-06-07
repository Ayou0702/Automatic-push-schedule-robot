package com.enterprise.service;

import com.enterprise.entity.CourseData;

import java.util.List;

public interface CourseDataService {

    boolean addCourseData(CourseData courseData);

    boolean deleteCourseData(int courseId);

    boolean updateCourseData(CourseData courseData);

    List<CourseData> queryAllCourseData();

    CourseData queryCourseDataByCourseId(int courseId);

}
