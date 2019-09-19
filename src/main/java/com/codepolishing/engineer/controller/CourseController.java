package com.codepolishing.engineer.controller;

import com.codepolishing.engineer.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    CourseRepository courseRepository;

    @GetMapping("/")
    public String showCourses(Model model){

        model.addAttribute("courses",courseRepository.findAll());

        return "courses";
    }
}
