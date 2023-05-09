package com.enterprise.entity;

import lombok.Data;

@Data
public class CourseData {

    private int courseId;
    private String courseName,courseVenue;
    private boolean courseSpecialized;
    public byte[] courseAvatar;

}
