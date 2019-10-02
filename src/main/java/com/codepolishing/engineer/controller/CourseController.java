package com.codepolishing.engineer.controller;

import com.codepolishing.engineer.entity.Course;
import com.codepolishing.engineer.entity.CourseSection;
import com.codepolishing.engineer.entity.User;
import com.codepolishing.engineer.repository.CourseLevelRepository;
import com.codepolishing.engineer.repository.CourseRepository;
import com.codepolishing.engineer.repository.CourseSectionRepository;
import com.codepolishing.engineer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    UserRepository userRepository;

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

    @RequestMapping("/joinCourse")
    public String joinCourse(@RequestParam("idCourse")int idCourse){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal instanceof UserDetails){
            User user = userRepository.findByEmail(((UserDetails) principal).getUsername());

            CourseSection courseSection = courseSectionRepository.findCourseSectionByIdCourseAndSectionPartIs(idCourse,1);

            user.getCourseSectionList().add(courseSection);

            userRepository.save(user);

            //TODO Add new column to joining section defining in which course u are
        }

        return "redirect:/courses/";
    }

}
