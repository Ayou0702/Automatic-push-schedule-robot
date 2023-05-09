package com.enterprise.service;

import com.enterprise.entity.CourseData;
import org.apache.ibatis.annotations.Update;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface CourseDataService {

    List<CourseData> queryAllCourseData();

    List<CourseData> queryAllCourseIdAndCourseName();

    CourseData queryCourseDataByCourseId(int courseId);

    CourseData queryCourseAvatarByTeacherId(int courseId);

    boolean updateCourseData(CourseData courseData);

    boolean modifyCourseAvatar(ByteArrayInputStream courseAvatar, int courseId);

    boolean deleteCourseAvatar(int courseId);

    boolean deleteCourseData(int courseId);

    boolean addCourseData(CourseData courseData);

}
