package com.raxrot.teacher_service.dto;

import lombok.Data;

@Data
public class TeacherResponse {
    private Long id;
    private String name;
    private String subject;
    private String email;
}
