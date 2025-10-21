package com.raxrot.dto;

import com.raxrot.client.TeacherResponse;
import lombok.Data;

import java.util.List;

@Data
public class CourseFullResponse {
    private TeacherResponse teacherResponse;
    private List<CourseResponse>courses;
}
