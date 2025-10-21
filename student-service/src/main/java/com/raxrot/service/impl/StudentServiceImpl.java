package com.raxrot.service.impl;

import com.raxrot.client.CourseClient;
import com.raxrot.client.CourseResponse;
import com.raxrot.client.StudentFullResponse;
import com.raxrot.dto.StudentRequest;
import com.raxrot.dto.StudentResponse;
import com.raxrot.exception.ApiException;
import com.raxrot.model.Student;
import com.raxrot.repository.StudentRepository;
import com.raxrot.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;
    private final CourseClient courseClient;

    @Override
    public StudentResponse createStudent(StudentRequest studentRequest) {
        Student student = modelMapper.map(studentRequest, Student.class);
        Student savedStudent = studentRepository.save(student);
        return modelMapper.map(savedStudent, StudentResponse.class);
    }

    @Override
    public List<StudentResponse> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        List<StudentResponse> studentResponses = students.stream()
                .map(student -> modelMapper.map(student, StudentResponse.class))
                .collect(Collectors.toList());
        return studentResponses;
    }

    @Override
    public StudentResponse getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(()-> new ApiException("Student not found", HttpStatus.NOT_FOUND));
        return modelMapper.map(student, StudentResponse.class);
    }

    // FeignClient
    @Override
    public StudentFullResponse getStudentFullResponse(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ApiException("Student not found", HttpStatus.NOT_FOUND));

        List<CourseResponse> courseResponses = new ArrayList<>();

        if (student.getCourseIds() != null && !student.getCourseIds().isEmpty()) {
            for (Long courseId : student.getCourseIds()) {
                try {
                    CourseResponse course = courseClient.getCourse(courseId);
                    courseResponses.add(course);
                } catch (Exception e) {
                    System.out.println("Course with ID " + courseId + " not found");
                }
            }
        }

        StudentFullResponse response = new StudentFullResponse();
        response.setStudentResponse(modelMapper.map(student, StudentResponse.class));
        response.setCourses(courseResponses);

        return response;
    }

    @Override
    public StudentResponse updateStudent(Long id, StudentRequest studentRequest) {
        Student student = studentRepository.findById(id)
                .orElseThrow(()-> new ApiException("Student not found", HttpStatus.NOT_FOUND));
        student.setName(studentRequest.getName());
        student.setEmail(studentRequest.getEmail());
        student.setCourseIds(studentRequest.getCourseIds());
        Student updatedStudent = studentRepository.save(student);
        return modelMapper.map(updatedStudent,StudentResponse.class);
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(()-> new ApiException("Student not found", HttpStatus.NOT_FOUND));
        studentRepository.delete(student);
    }
}
