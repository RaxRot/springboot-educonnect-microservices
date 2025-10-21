package com.raxrot.service;

import com.raxrot.client.TeacherClient;
import com.raxrot.client.TeacherResponse;
import com.raxrot.dto.CourseFullResponse;
import com.raxrot.dto.CourseRequest;
import com.raxrot.dto.CourseResponse;
import com.raxrot.exception.ApiException;
import com.raxrot.model.Course;
import com.raxrot.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;
    private final TeacherClient teacherClient;


    @Override
    public CourseResponse createCourse(CourseRequest courseRequest) {

        TeacherResponse teacherResponse = teacherClient.getTeacherById(courseRequest.getTeacherId());
        if (teacherResponse == null) {
            throw new ApiException("Teacher not found", HttpStatus.NOT_FOUND);
        }

        Course course = modelMapper.map(courseRequest, Course.class);

        //fix bug with creation
        course.setId(null);

        Course savedCourse = courseRepository.save(course);
        return modelMapper.map(savedCourse, CourseResponse.class);
    }

    @Override
    public List<CourseResponse> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        List<CourseResponse> courseResponses = courses.stream()
                .map(courseResponse -> modelMapper.map(courseResponse, CourseResponse.class))
                .collect(Collectors.toList());
        return courseResponses;
    }

    @Override
    public CourseResponse getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(()-> new ApiException("Course not found",HttpStatus.NOT_FOUND));
        return modelMapper.map(course, CourseResponse.class);
    }

    @Override
    public CourseFullResponse getAllCoursesByTeacherId(Long teacherId) {
        TeacherResponse teacherResponse = teacherClient.getTeacherById(teacherId);
        if (teacherResponse == null) {
            throw new ApiException("Teacher not found", HttpStatus.NOT_FOUND);
        }

        List<Course>allCourses=courseRepository.findByTeacherId(teacherResponse.getId());
        List<CourseResponse> allCoursesResponse = allCourses.stream()
                .map(element -> modelMapper.map(element, CourseResponse.class))
                .collect(Collectors.toList());

        CourseFullResponse courseFullResponse=new CourseFullResponse();
        courseFullResponse.setTeacherResponse(teacherResponse);
        courseFullResponse.setCourses(allCoursesResponse);
        return courseFullResponse;
    }

    @Override
    public CourseResponse updateCourse(Long id, CourseRequest courseRequest) {
        Course course = courseRepository.findById(id)
                .orElseThrow(()-> new ApiException("Course not found",HttpStatus.NOT_FOUND));
        TeacherResponse teacherResponse = teacherClient.getTeacherById(courseRequest.getTeacherId());
        if (teacherResponse == null) {
            throw new ApiException("Teacher not found", HttpStatus.NOT_FOUND);
        }
        course.setDescription(courseRequest.getDescription());
        course.setTitle(courseRequest.getTitle());
        course.setTeacherId(courseRequest.getTeacherId());
        return modelMapper.map(courseRepository.save(course), CourseResponse.class);
    }

    @Override
    public void deleteCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(()-> new ApiException("Course not found",HttpStatus.NOT_FOUND));
        courseRepository.delete(course);
    }
}
