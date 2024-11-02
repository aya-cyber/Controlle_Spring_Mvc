package com.example.student_course_management.repositories;

import com.example.student_course_management.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    // Additional query methods (if needed) can be defined here
}
