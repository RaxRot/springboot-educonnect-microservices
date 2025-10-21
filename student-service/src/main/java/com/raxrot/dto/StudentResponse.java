package com.raxrot.dto;

import lombok.Data;

import java.util.List;
@Data
public class StudentResponse {
    private Long id;
    private String name;
    private String email;
    private List<Long> courseIds;
}
