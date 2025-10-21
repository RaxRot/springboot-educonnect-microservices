package com.raxrot.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "course")
public interface CourseClient {
    @GetMapping("/api/courses/{id}")
    CourseResponse getCourse( @PathVariable Long id);
}
