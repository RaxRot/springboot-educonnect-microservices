package com.raxrot.teacher_service.controller;

import com.raxrot.teacher_service.dto.TeacherRequest;
import com.raxrot.teacher_service.dto.TeacherResponse;
import com.raxrot.teacher_service.service.TeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;

    @PostMapping
    public ResponseEntity<TeacherResponse> createTeacher(@Valid @RequestBody TeacherRequest teacherRequest) {
        return new ResponseEntity<>(teacherService.createTeacher(teacherRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TeacherResponse>> getAllTeachers() {
        return ResponseEntity.ok(teacherService.findAllTeachers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherResponse> getTeacher(@PathVariable Long id) {
        return ResponseEntity.ok(teacherService.findTeacherById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherResponse> updateTeacher(@PathVariable Long id,
                                                         @Valid @RequestBody TeacherRequest teacherRequest) {
        return ResponseEntity.ok(teacherService.updateTeacher(id, teacherRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.noContent().build();
    }
}
