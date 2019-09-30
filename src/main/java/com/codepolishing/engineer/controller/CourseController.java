package com.codepolishing.engineer.controller;

import com.codepolishing.engineer.entity.Course;
import com.codepolishing.engineer.entity.CourseSection;
import com.codepolishing.engineer.repository.CourseLevelRepository;
import com.codepolishing.engineer.repository.CourseRepository;
import com.codepolishing.engineer.repository.CourseSectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CourseLevelRepository courseLevelRepository;

    @Autowired
    CourseSectionRepository courseSectionRepository;

    @GetMapping("/")
    public String showCourses(Model model){

        model.addAttribute("basicCourses",courseRepository.findCoursesByIdCourseLevel(1));
        model.addAttribute("mediumCourses",courseRepository.findCoursesByIdCourseLevel(2));
        model.addAttribute("hardCourses",courseRepository.findCoursesByIdCourseLevel(3));

        model.addAttribute("courseLevelName",courseLevelRepository.findAll());

        return "courses";
    }

    @GetMapping("/{id}")
    public String showOneCourse(@PathVariable("id")int id, Model model){

        Course course = courseRepository.getOne(id);

        List<CourseSection> courseSectionList = courseSectionRepository.findCourseSectionsByIdCourse(id);

        model.addAttribute("course",course);
        model.addAttribute("courseSections",courseSectionList);

        return "single_course";
    }
}
