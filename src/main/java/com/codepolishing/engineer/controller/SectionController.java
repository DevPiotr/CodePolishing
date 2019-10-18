package com.codepolishing.engineer.controller;

import com.codepolishing.engineer.entity.CourseSection;
import com.codepolishing.engineer.entity.CourseSubsection;
import com.codepolishing.engineer.entity.Theory;
import com.codepolishing.engineer.repository.CourseSectionRepository;
import com.codepolishing.engineer.repository.CourseSubsectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/sections")
public class SectionController {

    @Autowired
    CourseSectionRepository courseSectionRepository;

    @Autowired
    CourseSubsectionRepository courseSubsectionRepository;

    @RequestMapping("/{idCourseSection}")
    public String showOneCourseSection(@PathVariable("idCourseSection")int idCourseSection,@RequestParam("idSub")int idCourseSubsection,@RequestParam("part")int part, Model model){

        CourseSection courseSection = courseSectionRepository.getOne(idCourseSection);

        CourseSubsection courseSubsection = courseSubsectionRepository.getOne(idCourseSubsection);

        model.addAttribute("courseSubsectionList", courseSection.getCourseSubsectionList());
        model.addAttribute("theory",courseSubsection.getTheoryList().get(part));
        model.addAttribute("nextPart",++part);
        model.addAttribute("currentSubSectionId",idCourseSubsection);

        return "section_training";

    }

}
