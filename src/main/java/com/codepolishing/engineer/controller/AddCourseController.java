package com.codepolishing.engineer.controller;

import com.codepolishing.engineer.entity.Course;
import com.codepolishing.engineer.entity.User;
import com.codepolishing.engineer.repository.CourseLevelRepository;
import com.codepolishing.engineer.repository.CourseTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class AddCourseController {

    @Autowired
    CourseTypeRepository courseTypeRepository;

    @Autowired
    CourseLevelRepository courseLevelRepository;

    @GetMapping("/addCourse")
    public String showAddCourseForm(Course course,Model model){

        model.addAttribute("course",course);
        model.addAttribute("courseTypes",courseTypeRepository.findAll());
        model.addAttribute("courseLevels",courseLevelRepository.findAll());

        return "add_course";
    }

    @PostMapping("/addCourse")
    public String addCourse(@Valid @ModelAttribute("course") Course course,
                            @RequestParam("numberOfSections")int numberOfSections,
                            BindingResult bindingResult,
                            Model model){
        if (bindingResult.hasErrors()) {
            return "add_course";
        }else {

            model.addAttribute("numberOfSections",numberOfSections);
            return "add_sections";
        }
    }
}
