package com.codepolishing.engineer.controller.Edit;

import com.codepolishing.engineer.entity.Course;
import com.codepolishing.engineer.entity.CourseSection;
import com.codepolishing.engineer.entity.CourseSubsection;
import com.codepolishing.engineer.repository.CourseSectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping("/courses/editSections")
public class EditSectionController {

    @Autowired
    CourseSectionRepository courseSectionRepository;


    //variable used to store id for editing
    int rememberCourseId;

    int rememberCourseSectionId;

    @GetMapping("/{id}")
    public String showSectionToEdit(@PathVariable("id")int courseId,
                                    Model model){

        List<CourseSection> courseSectionList = courseSectionRepository.findCourseSectionsByIdCourse(courseId);

        rememberCourseId = courseId;

        model.addAttribute("courseSections",courseSectionList);
        model.addAttribute("edit","Edytuj");

        return "course_sections";
    }

    @GetMapping("/delete/{id}")
    public String deleteSection(@PathVariable("id")int courseSectionId){

        courseSectionRepository.deleteById(courseSectionId);

        return "redirect:/courses/editSections/" + rememberCourseId;
    }

    @GetMapping("/edit/{id}")
    public String showFormToEditSection(@PathVariable("id")int courseSectionId,
                                        Model model){
        CourseSection courseSection = courseSectionRepository.getOne(courseSectionId);

        model.addAttribute("courseSection", courseSection);

        return "edit_section_from_existed_course";
    }

    @PostMapping("/edit/{id}")
    public String editSection(@Valid @ModelAttribute("course")CourseSection editedCourseSection,
                              BindingResult bindingResult,
                              @PathVariable("id")int courseSectionId){
        if(bindingResult.hasFieldErrors()){
            return "edit_section_from_existed_course";
        }

        CourseSection courseSectionToSave = courseSectionRepository.getOne(courseSectionId);

        courseSectionToSave.setName(editedCourseSection.getName());
        courseSectionToSave.setShortDescription(editedCourseSection.getShortDescription());
        courseSectionToSave.setModificationDate(new Date(System.currentTimeMillis()));

        courseSectionRepository.saveAndFlush(courseSectionToSave);

        return "redirect:/courses/editSections/" + rememberCourseId;
    }
}
