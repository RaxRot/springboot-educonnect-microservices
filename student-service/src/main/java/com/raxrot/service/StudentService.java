package com.raxrot.service;

import com.raxrot.client.StudentFullResponse;
import com.raxrot.dto.StudentRequest;
import com.raxrot.dto.StudentResponse;

import java.util.List;

public interface StudentService {
    StudentResponse createStudent(StudentRequest studentRequest);
    List<StudentResponse> getAllStudents();
    StudentResponse getStudentById(Long id);
    StudentFullResponse getStudentFullResponse(Long id);//feign тут
    StudentResponse updateStudent(Long id,StudentRequest studentRequest);
    void deleteStudent(Long id);
}
