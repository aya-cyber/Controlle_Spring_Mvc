package com.example.student_course_management.repositories;

import com.example.student_course_management.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    // Vous pouvez ajouter des méthodes personnalisées ici si nécessaire
}
