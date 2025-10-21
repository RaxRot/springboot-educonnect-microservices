package com.raxrot.teacher_service.service;

import com.raxrot.teacher_service.dto.TeacherRequest;
import com.raxrot.teacher_service.dto.TeacherResponse;

import java.util.List;

public interface TeacherService {
    TeacherResponse createTeacher(TeacherRequest teacherRequest);
    TeacherResponse findTeacherById(Long id);
    List<TeacherResponse> findAllTeachers();
    TeacherResponse updateTeacher(Long id, TeacherRequest teacherRequest);
    void deleteTeacher(Long id);
}
