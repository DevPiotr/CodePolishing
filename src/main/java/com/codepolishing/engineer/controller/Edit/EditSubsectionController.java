package com.codepolishing.engineer.controller.Edit;

import com.codepolishing.engineer.entity.CompileTask;
import com.codepolishing.engineer.entity.Task;
import com.codepolishing.engineer.entity.Theory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
@RequestMapping("/courses/editSections/editSubsection")
public class EditSubsectionController {

    int rememberCourseSectionId;
    String rememberSubsectionName;


    ArrayList<Task> taskList;
    ArrayList<Theory> theoryList;
    ArrayList<CompileTask> compileTaskList;

    private final String contentView = "subsection_management/add_subsection_content";
    private final String taskView = "subsection_management/add_subsection_task";

    @GetMapping(value = "/addContent", params = {"sectionId","subSectionName"})
    public String showFormToAddSubsectionContent(@RequestParam("sectionId")int courseSectionId,
                                                 @RequestParam("subSectionName")String subSectionName,
                                                 Theory theory,
                                                 Model model){

        rememberCourseSectionId = courseSectionId;
        rememberSubsectionName = subSectionName;

        taskList = new ArrayList<>();
        theoryList = new ArrayList<>();
        compileTaskList = new ArrayList<>();

        setListInModel(model);

        model.addAttribute("theory",theory);

        return contentView;
    }

    //region Content methods
    @GetMapping("/addContent")
    public String showFormToAddSubsectionContent(Theory theory,
                                                 Model model){

        setListInModel(model);

        model.addAttribute("theory",theory);
        return contentView;
    }

    @PostMapping("/addContent/add")
    public String addContentToList(@Valid @ModelAttribute("theory")Theory theory,
                                BindingResult bindingResult){
        if(bindingResult.hasErrors())
            System.out.println(bindingResult);
        else
            theoryList.add(theory);

        return "redirect:/courses/editSections/editSubsection/addContent";
    }

    @PostMapping("/addContent/delete")
    public String deleteContent(@RequestParam("deleteNumber")int number){

        if(!theoryList.isEmpty()) {
            theoryList.remove(number);
        }

        return "redirect:/courses/editSections/editSubsection/addContent";
    }
    //endregion

    // region Task methods
    @GetMapping("/addTask")
    public String showFormToAddSubsectionTask(Task task,
                                              Model model){

        setListInModel(model);

        model.addAttribute("task",task);
        return taskView;
    }

    @PostMapping("/addTask/add")
    public String addTaskToList(@Valid @ModelAttribute("task")Task task,
                                BindingResult bindingResult){
        if(bindingResult.hasErrors())
            System.out.println(bindingResult);
        else
            taskList.add(task);

        return "redirect:/courses/editSections/editSubsection/addTask";
    }

    @PostMapping("/addTask/delete")
    public String delete(@RequestParam("deleteNumber")int number){

        if(!taskList.isEmpty()) {
            taskList.remove(number);
        }

        return "redirect:/courses/editSections/editSubsection/addTask";
    }

    //endregion

    private void setListInModel(Model model){
            model.addAttribute("taskList",taskList);
            model.addAttribute("theoryList",theoryList);
            model.addAttribute("compileTaskList",compileTaskList);
    }
}
