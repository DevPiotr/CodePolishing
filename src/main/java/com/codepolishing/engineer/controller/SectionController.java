package com.codepolishing.engineer.controller;

import com.codepolishing.engineer.entity.*;
import com.codepolishing.engineer.repository.CourseSectionRepository;
import com.codepolishing.engineer.repository.CourseSubsectionRepository;
import com.codepolishing.engineer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;


@Controller
@RequestMapping("/sections")
public class SectionController {

    @Autowired
    CourseSectionRepository courseSectionRepository;

    @Autowired
    CourseSubsectionRepository courseSubsectionRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/{idCourseSection}")
    public String showOneCourseSubsectionContent(@PathVariable("idCourseSection")int idCourseSection,
                                                 @RequestParam("idSub")int idCourseSubsection,
                                                 @RequestParam("part")int part,
                                                 @RequestParam("contentType")String contentType,
                                                 Principal principal,
                                                 Model model){

        CourseSection courseSection = courseSectionRepository.getOne(idCourseSection);
        CourseSubsection courseSubsection = courseSubsectionRepository.getOne(idCourseSubsection);

        User user = userRepository.findByEmail(principal.getName());

        model.addAttribute("courseSubsectionList", courseSection.getCourseSubsectionList());
        model.addAttribute("finishedSubSections",user.getFinishedSubsectionList());
        model.addAttribute("currentSubSectionId",idCourseSubsection);

        if(contentType.equals("theory") && courseSubsection.getTheoryList().size() <= part){
            contentType = "task";
            part = 0;
        }

        if(contentType.equals("task") && courseSubsection.getTaskList().size() <= part){

            if(!user.getFinishedSubsectionList().contains(courseSubsection)) {
                user.getFinishedSubsectionList().add(courseSubsection);

                userRepository.saveAndFlush(user);
            }
            return "congrats_subsection";
        }

        switch(contentType){
            case "theory":{
                model.addAttribute("theory",courseSubsection.getTheoryList().get(part));
                model.addAttribute("nextPart",++part);
                return "section_training";
            }
            case "task":{

                Task task = courseSubsection.getTaskList().get(part);
                model.addAttribute("nextPart",++part);

                String[] allAnswers = task.getAllAnswers().split(";");

                model.addAttribute("allAnswers",allAnswers);
                model.addAttribute("rightAnswers",task.getRightAnswer());
                model.addAttribute("task",task);

                return "section_abcd";
            }
            default:{
                return "index";
            }
        }
    }

    @PostMapping("/{idCourseSection}")
    public String checkAnswerInCloseTask(@PathVariable("idCourseSection")int idCourseSection,
                                         @RequestParam("rightAnswer")String rightAnswer,
                                         @RequestParam("idSub")int idCourseSubsection,
                                         @RequestParam("part")int part,
                                         @RequestParam("chosen")String chosen,
                                         Principal principal,
                                         Model model){
        User user = userRepository.findByEmail(principal.getName());

        CourseSection courseSection = courseSectionRepository.getOne(idCourseSection);
        CourseSubsection courseSubsection = courseSubsectionRepository.getOne(idCourseSubsection);
        // Part is variable that is used to get next task that's why if we want to check current task we have to get part-1
        Task task = courseSubsection.getTaskList().get(part-1);


        model.addAttribute("courseSubsectionList", courseSection.getCourseSubsectionList());
        model.addAttribute("finishedSubSections",user.getFinishedSubsectionList());

        model.addAttribute("currentSubSectionId",idCourseSubsection);
        model.addAttribute("nextPart",part);

        String[] allAnswers = task.getAllAnswers().split(";");

        model.addAttribute("allAnswers",allAnswers);
        model.addAttribute("rightAnswers",task.getRightAnswer());
        model.addAttribute("task",task);

        if(chosen.equals(rightAnswer)){
            model.addAttribute("afterCheck","Brawo");
        }

        return "section_abcd";

    }

}
