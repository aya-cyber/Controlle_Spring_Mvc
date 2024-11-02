package com.example.student_course_management.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        // You can add attributes to the model if needed
        return "home"; // Return the name of the Thymeleaf template for the home page
    }
}
