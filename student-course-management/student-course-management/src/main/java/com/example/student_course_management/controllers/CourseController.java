package com.example.student_course_management.controllers;

import com.example.student_course_management.entities.Course;
import com.example.student_course_management.entities.Enrollment;
import com.example.student_course_management.entities.Student;
import com.example.student_course_management.repositories.CourseRepository;
import com.example.student_course_management.repositories.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/courses")
public class CourseController {

    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository; // Add this repository for managing enrollments

    @Autowired
    public CourseController(CourseRepository courseRepository, EnrollmentRepository enrollmentRepository) {
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository; // Initialize the enrollment repository
    }

    // Display the list of courses
    @GetMapping
    public String listCourses(Model model) {
        List<Course> courses = courseRepository.findAll();
        model.addAttribute("courses", courses);
        return "course/list"; // Path to the Thymeleaf template
    }

    // Display form to add a new course
    @GetMapping("/add")
    public String showAddCourseForm(Model model) {
        model.addAttribute("course", new Course());
        return "course/add"; // Path to the Thymeleaf template
    }

    // Handle form submission for adding a new course
    @PostMapping("/add")
    public String addCourse(@ModelAttribute Course course, RedirectAttributes redirectAttributes) {
        courseRepository.save(course);
        redirectAttributes.addFlashAttribute("successMessage", "Course added successfully!");
        return "redirect:/courses"; // Redirect to the list of courses
    }

    // Display form to edit an existing course
    @GetMapping("/edit")
    public String showEditCourseForm(@RequestParam("id") Long id, Model model) {
        Course course = courseRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid course Id: " + id));
        model.addAttribute("course", course);
        return "course/edit"; // Path to the Thymeleaf template
    }

    // Handle form submission for updating an existing course
    @PostMapping("/edit")
    public String updateCourse(@RequestParam("id") Long id, @ModelAttribute Course course, RedirectAttributes redirectAttributes) {
        // Ensure the ID in the course entity matches the path variable
        course.setId(id);
        courseRepository.save(course);
        redirectAttributes.addFlashAttribute("successMessage", "Course updated successfully!");
        return "redirect:/courses"; // Redirect to the list of courses
    }

    // Handle course deletion
    @GetMapping("/delete")
    public String deleteCourse(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        if (!courseRepository.existsById(id)) {
            throw new IllegalArgumentException("Invalid course Id: " + id);
        }
        courseRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Course deleted successfully!");
        return "redirect:/courses"; // Redirect to the list of courses
    }

    // Display details of a specific course
    @GetMapping("/detail")
    public String courseDetails(@RequestParam("id") Long id, Model model) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid course Id: " + id)); // Get course by ID
        model.addAttribute("course", course);

        // Fetch the students enrolled in the course
        List<Student> enrolledStudents = course.getEnrollments().stream()
                .map(Enrollment::getStudent)
                .collect(Collectors.toList());
        model.addAttribute("enrolledStudents", enrolledStudents); // Add the list of enrolled students to the model

        return "course/detail"; // Path to the Thymeleaf template for course details
    }


}
