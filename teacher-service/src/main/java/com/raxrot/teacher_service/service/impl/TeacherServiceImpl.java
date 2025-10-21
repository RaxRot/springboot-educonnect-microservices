package com.raxrot.teacher_service.service.impl;

import com.raxrot.teacher_service.dto.TeacherRequest;
import com.raxrot.teacher_service.dto.TeacherResponse;
import com.raxrot.teacher_service.exception.ApiException;
import com.raxrot.teacher_service.model.Teacher;
import com.raxrot.teacher_service.repository.TeacherRepository;
import com.raxrot.teacher_service.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;
    private final ModelMapper modelMapper;

    @Override
    public TeacherResponse createTeacher(TeacherRequest teacherRequest) {
        Teacher teacher = modelMapper.map(teacherRequest, Teacher.class);
        Teacher savedTeacher = teacherRepository.save(teacher);
        return modelMapper.map(savedTeacher, TeacherResponse.class);
    }

    @Override
    public TeacherResponse findTeacherById(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(()-> new ApiException("Teacher not found", HttpStatus.NOT_FOUND));
        return modelMapper.map(teacher, TeacherResponse.class);
    }

    @Override
    public List<TeacherResponse> findAllTeachers() {
        List<Teacher> teachers = teacherRepository.findAll();
        List<TeacherResponse> teacherResponses = teachers.stream()
                .map(teacher -> modelMapper.map(teacher, TeacherResponse.class))
                .collect(Collectors.toList());
        return teacherResponses;
    }

    @Override
    public TeacherResponse updateTeacher(Long id, TeacherRequest teacherRequest) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(()-> new ApiException("Teacher not found", HttpStatus.NOT_FOUND));
        teacher.setName(teacherRequest.getName());
        teacher.setEmail(teacherRequest.getEmail());
        teacher.setSubject(teacherRequest.getSubject());
        Teacher savedTeacher = teacherRepository.save(teacher);
        return modelMapper.map(savedTeacher, TeacherResponse.class);
    }

    @Override
    public void deleteTeacher(Long id) {
        if (teacherRepository.existsById(id)) {
            teacherRepository.deleteById(id);
        }else{
            throw new ApiException("Teacher not found", HttpStatus.NOT_FOUND);
        }
    }
}
