package com.raxrot.client;

import com.raxrot.dto.StudentResponse;
import lombok.Data;

import java.util.List;

@Data
public class StudentFullResponse {
 private StudentResponse studentResponse;
 private List<CourseResponse>courses;
}
