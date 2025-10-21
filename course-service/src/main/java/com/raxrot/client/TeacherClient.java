package com.raxrot.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "teacher")
public interface TeacherClient {
    @GetMapping("/api/teachers/{id}")
    TeacherResponse getTeacherById(@PathVariable Long id);
}
