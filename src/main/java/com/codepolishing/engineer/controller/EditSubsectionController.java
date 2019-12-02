package com.codepolishing.engineer.controller;

import com.codepolishing.engineer.entity.CompileTask;
import com.codepolishing.engineer.entity.Task;
import com.codepolishing.engineer.entity.Theory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
@RequestMapping("/courses/editSections/editSubsection")
public class EditSubsectionController {

    int rememberCourseSectionId;
    String rememberSubsectionName;


    ArrayList<Task> taskList;
    ArrayList<Theory> theoryList;
    ArrayList<CompileTask> compileTaskList;

    @GetMapping(value = "/addContent", params = {"sectionId","subSectionName"})
    public String showFormToAddSubsectionContent(@RequestParam("sectionId")int courseSectionId,
                                                 @RequestParam("subSectionName")String subSectionName,
                                                 Theory theory,
                                                 Model model){
        rememberCourseSectionId = courseSectionId;
        rememberSubsectionName = subSectionName;

        model.addAttribute("theory",theory);
        return "add_subsection_content";
    }

    @GetMapping("/addContent")
    public String showFormToAddSubsectionContent(Theory theory,
                                                 Model model){
        model.addAttribute("theory",theory);
        return "add_subsection_content";
    }
}
