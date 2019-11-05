package com.codepolishing.engineer.controller;

import com.codepolishing.engineer.entity.*;
import com.codepolishing.engineer.repository.CourseSectionRepository;
import com.codepolishing.engineer.repository.CourseSubsectionRepository;
import com.codepolishing.engineer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

        User user = userRepository.findByEmail(principal.getName());

        initModelValues(model,user,idCourseSection,idCourseSubsection);

        CourseSubsection courseSubsection = courseSubsectionRepository.getOne(idCourseSubsection);


        if(contentType.equals("theory") && courseSubsection.getTheoryList().size() <= part){
            contentType = "task";
            part = 0;
        }

        if(contentType.equals("task") && courseSubsection.getTaskList().size() <= part){

            if(!user.getFinishedSubsectionList().contains(courseSubsection)) {
                user.getFinishedSubsectionList().add(courseSubsection);

                user.setScore(user.getScore() + 100);
                userRepository.saveAndFlush(user);
            }

            if(!courseSubsection.getCompileTaskList().isEmpty()){
                //TODO Add to model
            }
            return "congrats_subsection";
        }

        switch(contentType){
            case "theory":{

                prepareModelForTraining(model,courseSubsection,part);

                return "section_training";
            }
            case "task":{

                prepareModelForTask(model,courseSubsection,part);

                return "section_abcd";
            }
            default:{
                return "index.html";
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
        initModelValues(model,user,idCourseSection,idCourseSubsection);

        prepareModelForTaskAfterChecking(model,courseSubsectionRepository.getOne(idCourseSubsection),part);

        if(chosen.equals(rightAnswer)){
            model.addAttribute("afterCheck","Brawo");
        }

        return "section_abcd";

    }

    private void initModelValues(Model model, User user, int idCourseSection, int idCourseSubsection) {
        CourseSection courseSection = courseSectionRepository.getOne(idCourseSection);

        model.addAttribute("courseSubsectionList", courseSection.getCourseSubsectionList());
        model.addAttribute("finishedSubSections",user.getFinishedSubsectionList());
        model.addAttribute("currentSubSectionId",idCourseSubsection);

    }

    private void prepareModelForTraining(Model model, CourseSubsection courseSubsection, int part) {

        model.addAttribute("theory",courseSubsection.getTheoryList().get(part));
        model.addAttribute("nextPart",++part);

    }

    private void prepareModelForTask(Model model, CourseSubsection courseSubsection, int part) {

        Task task = courseSubsection.getTaskList().get(part);
        model.addAttribute("nextPart",++part);

        String[] allAnswers = task.getAllAnswers().split(";");

        model.addAttribute("allAnswers",allAnswers);
        model.addAttribute("rightAnswers",task.getRightAnswer());
        model.addAttribute("task",task);
    }

    private void prepareModelForTaskAfterChecking(Model model, CourseSubsection courseSubsection, int part) {
        // Part is variable that is used to get next task that's why if we want to check current task we have to get part-1
        Task task = courseSubsection.getTaskList().get(part-1);

        model.addAttribute("nextPart",part);

        String[] allAnswers = task.getAllAnswers().split(";");

        model.addAttribute("allAnswers",allAnswers);
        model.addAttribute("rightAnswers",task.getRightAnswer());
        model.addAttribute("task",task);
    }

}
