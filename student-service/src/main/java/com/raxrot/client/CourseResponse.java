package com.raxrot.client;

import lombok.Data;

@Data
public class CourseResponse {
    private Long id;
    private String title;
    private String description;
    private Long teacherId;
}
