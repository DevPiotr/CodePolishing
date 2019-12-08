package com.codepolishing.engineer.controller.Edit;

import com.codepolishing.engineer.entity.Course;
import com.codepolishing.engineer.entity.CourseSection;
import com.codepolishing.engineer.repository.CourseLevelRepository;
import com.codepolishing.engineer.repository.CourseRepository;
import com.codepolishing.engineer.repository.CourseSectionRepository;
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
import java.util.List;

@Controller
@RequestMapping("/courses")
public class EditCourseController {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CourseLevelRepository courseLevelRepository;

    @Autowired
    CourseTypeRepository courseTypeRepository;

    @Autowired
    CourseSectionRepository courseSectionRepository;

    Course courseToAddNewSection;

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

    @GetMapping("/addSection/{id}")
    public String showAddSectionForm(@PathVariable("id")int courseId,
                                     CourseSection courseSection,
                                     Model model){
        courseToAddNewSection = courseRepository.getOne(courseId);

        model.addAttribute("courseSection", courseSection);

        return "add_section_to_existed_course";
    }

    @PostMapping("/addSection")
    public String addSectionToCourse(@Valid @ModelAttribute("courseSection")CourseSection courseSection,
                                     BindingResult bindingResult){
        if(bindingResult.hasFieldErrors("createDate")){
            courseSection.setCreateDate(new Date(Calendar.getInstance().getTimeInMillis()));
        }
        if (bindingResult.hasErrors() && !bindingResult.hasFieldErrors("createDate")) {

            return "add_section_to_existed_course";
        }else {
            courseSection.setIdCourse(courseToAddNewSection.getId());
            CourseSection courseSectionToAdd = courseSectionRepository.saveAndFlush(courseSection);

            courseToAddNewSection.getCourseSectionList().add(courseSectionToAdd);
            courseRepository.saveAndFlush(courseToAddNewSection);


            return "redirect:/courses/" + courseToAddNewSection.getId();
        }

    }
}
