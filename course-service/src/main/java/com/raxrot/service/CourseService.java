package com.raxrot.service;

import com.raxrot.dto.CourseFullResponse;
import com.raxrot.dto.CourseRequest;
import com.raxrot.dto.CourseResponse;

import java.util.List;

public interface CourseService {
    CourseResponse createCourse(CourseRequest courseRequest);
    List<CourseResponse> getAllCourses();
    CourseResponse getCourseById(Long id);
    CourseFullResponse getAllCoursesByTeacherId(Long teacherId);//НАДО БУДЕТ ЧЕРЕЗ FEIGH!
    CourseResponse updateCourse(Long id,CourseRequest courseRequest);
    void deleteCourse(Long id);
}
