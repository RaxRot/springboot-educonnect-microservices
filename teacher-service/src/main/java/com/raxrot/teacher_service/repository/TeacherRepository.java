package com.raxrot.teacher_service.repository;

import com.raxrot.teacher_service.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
