package com.codepolishing.engineer.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

@Controller
public class AddCourseController {

    @Autowired
    CourseTypeRepository courseTypeRepository;

    @Autowired
    CourseLevelRepository courseLevelRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CourseSectionRepository courseSectionRepository;

    Course createdCurse;

    ArrayList<CourseSection> newCourseSections;

    @GetMapping("/addCourse")
    public String showAddCourseForm(Course course,Model model){

        model.addAttribute("course",course);
        model.addAttribute("courseTypes",courseTypeRepository.findAll());
        model.addAttribute("courseLevels",courseLevelRepository.findAll());

        return "add_course";
    }

    @PostMapping("/addCourse")
    public String addCourse(@Valid @ModelAttribute("course") Course course,
                            BindingResult bindingResult,
                            Model model){
        if(bindingResult.hasFieldErrors("createDate")){
            course.setCreateDate(new Date(Calendar.getInstance().getTimeInMillis()));
        }
        if (bindingResult.hasErrors() && !bindingResult.hasFieldErrors("createDate")) {
            model.addAttribute("courseTypes",courseTypeRepository.findAll());
            model.addAttribute("courseLevels",courseLevelRepository.findAll());
            return "add_course";
        }else {
            createdCurse = course;

            return "redirect:/addSection";
        }
    }

    @GetMapping("/addSection")
    public String showAddSectionForm(CourseSection courseSection,
                                     Model model){

        newCourseSections = new ArrayList<>();

        model.addAttribute("courseName",createdCurse.getName());
        model.addAttribute("courseSection",courseSection);

        return "add_sections";
    }

    @PostMapping("/addSection")
    public String addSectionToList(@Valid @ModelAttribute("courseSection")CourseSection courseSection,
                                      BindingResult bindingResult,
                                      Model model){
        if(bindingResult.hasFieldErrors("name") ||
            bindingResult.hasFieldErrors("shortDescription")){
            return "add_sections";
        }

        for(CourseSection cs : newCourseSections){
            if(cs.getName().equals(courseSection.getName())){
                model.addAttribute("errorMessage","Rozdział o takiej nazwie już istnieje");
                initModelAttribute(model);
                return "add_sections";
            }
        }
        courseSection.setCreateDate(new Date(Calendar.getInstance().getTimeInMillis()));

        newCourseSections.add(courseSection);

        initModelAttribute(model);

        return "add_sections";
    }

    @PostMapping("/deleteSection")
    public String deleteSectionFromList(@RequestParam("deleteNumber")int number,
                                        CourseSection courseSection,
                                        Model model){
        if(!newCourseSections.isEmpty()) {
            newCourseSections.remove(number);
        }

        initModelAttribute(model);

        model.addAttribute("courseSection",courseSection);

        return "add_sections";
    }

    @PostMapping("/finish")
    public String finishCreating(){

        ArrayList<CourseSection> courseSectionsWithId = new ArrayList<>();

        Course course = courseRepository.saveAndFlush(createdCurse);

        int part = 1;
        for(CourseSection cs: newCourseSections) {
            cs.setSectionPart(part++);
            cs.setIdCourse(course.getId());
            courseSectionsWithId.add(courseSectionRepository.saveAndFlush(cs));
        }
        course.setCourseSectionList(courseSectionsWithId);

        courseRepository.saveAndFlush(course);

        return "index";
    }

    private void initModelAttribute(Model model){
        model.addAttribute("courseName",createdCurse.getName());
        model.addAttribute("newCourseSections",newCourseSections);
    }
}
