package com.codepolishing.engineer.controller;

import com.codepolishing.engineer.entity.Course;
import com.codepolishing.engineer.entity.CourseLevel;
import com.codepolishing.engineer.repository.CourseLevelRepository;
import com.codepolishing.engineer.repository.CourseRepository;
import com.codepolishing.engineer.repository.CourseTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

@Controller
@RequestMapping("/courses")
public class EditCourse {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CourseLevelRepository courseLevelRepository;

    @Autowired
    CourseTypeRepository courseTypeRepository;

    @GetMapping("/edit/{id}")
    public String editCourse(@PathVariable("id")int id,
                             Model model){
        Course course = courseRepository.getOne(id);

        model.addAttribute("course",course);
        model.addAttribute("courseTypes",courseTypeRepository.findAll());
        model.addAttribute("courseLevels",courseLevelRepository.findAll());


        return "edit_course";
    }

    @PostMapping("/edit/{id}")
    public String saveEditedCourse(@Valid @ModelAttribute("course")Course course,
                                   BindingResult bindingResult,
                                   @PathVariable("id")int courseId,
                                   Model model){
        if(bindingResult.hasErrors()){
            System.out.println(bindingResult);
            model.addAttribute("courseTypes",courseTypeRepository.findAll());
            model.addAttribute("courseLevels",courseLevelRepository.findAll());
            return "edit_course";
        }

        Course editedCourse;

        course.setModificationDate(new Date(System.currentTimeMillis()));

        if(course.getCourseSectionList() == null){
            course.setCourseSectionList(new ArrayList<>());
        }

        editedCourse = course;

        courseRepository.save(editedCourse);

        return "redirect:/courses/";
    }

    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable("id")int courseId){

        courseRepository.deleteById(courseId);

        return "redirect:/courses/";
    }
}
