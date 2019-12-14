package com.codepolishing.engineer.controller.Edit;

import com.codepolishing.engineer.entity.*;
import com.codepolishing.engineer.repository.*;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/courses/editSections/editSubsection")
public class EditSubsectionController {

    @Autowired
    CourseSubsectionRepository courseSubsectionRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TheoryRepository theoryRepository;

    @Autowired
    CompileTaskRepository compileTaskRepository;

    @Autowired
    CourseSectionRepository courseSectionRepository;

    private CourseSubsection editedSubsection;
    private int rememberedSectionId;

    private List<Task> taskList;
    private List<Theory> theoryList;
    private List<CompileTask> compileTaskList;

    private final String contentView = "subsection_management/edit_subsection_content";
    private final String taskView = "subsection_management/edit_subsection_task";
    private final String compileTaskView = "subsection_management/edit_subsection_compile_task";
    private final String subsectionListView = "subsection_management/show_subsections_to_edit";


    @GetMapping("/delete/{id}")
    public String deleteCourseSubsection(@PathVariable("id")int courseSubsectionId){

        courseSubsectionRepository.deleteById(courseSubsectionId);

        return "redirect:/courses/editSections/editSubsection/show/"+ rememberedSectionId;
    }
    @GetMapping("/show/{id}")
    public String showSubsectionToEdit(@PathVariable("id")int courseSectionId,
                                       Model model){

        rememberedSectionId = courseSectionId;
        CourseSection courseSection = courseSectionRepository.getOne(courseSectionId);

        model.addAttribute("courseSubsection",courseSection.getCourseSubsectionList());

        return subsectionListView;
    }


    @GetMapping("/editContent/{id}")
    public String prepareForEditing(@PathVariable("id")int subsectionId,
                                                  Model model){

        editedSubsection = courseSubsectionRepository.getOne(subsectionId);

        setListInModel(model);

        return getPossibleRedirectValue();
    }

    //region Content methods
    @GetMapping(value = "/editContent", params = {"theoryId"})
    public String showFormToEditSubsectionContent(@RequestParam("theoryId")int theoryId,
                                                 Model model){

        setListInModel(model);

        model.addAttribute("theory",theoryRepository.getOne(theoryId));

        return contentView;
    }

    @PostMapping("/editContent/edit")
    public String EditContentFromList(@Valid @ModelAttribute("theory")Theory theory,
                                BindingResult bindingResult){
        if(bindingResult.hasErrors())
            System.out.println(bindingResult);
        else
            theoryRepository.save(theory);

        return "redirect:/courses/editSections/editSubsection/editContent?theoryId=" + theory.getId();
    }

    @PostMapping("/editContent/delete")
    public String deleteContentFromList(@RequestParam("deleteNumber")int number){

        theoryRepository.delete(theoryList.get(number));
        theoryList.remove(number);

        return getPossibleRedirectValue();
    }
    //endregion

    // region Task methods
    @GetMapping("/editTask")
    public String showFormToEditSubsectionTask(@RequestParam("taskId")int taskId,
                                              Model model){

        setListInModel(model);

        Task task = taskRepository.getOne(taskId);

        String[] answers = task.getAllAnswers().split(";");

        model.addAttribute("a",answers[0]);
        model.addAttribute("b",answers[1]);
        model.addAttribute("c",answers[2]);
        model.addAttribute("d",answers[3]);


        model.addAttribute("task",taskRepository.getOne(taskId));
        return taskView;
    }

    @PostMapping("/editTask/edit")
    public String editTaskToList(@Valid @ModelAttribute("task")Task task,
                                BindingResult bindingResult,
                                @RequestParam("a")String a,
                                @RequestParam("b")String b,
                                @RequestParam("c")String c,
                                @RequestParam("d")String d,
                                @RequestParam("rightAnswer")String rightAnswer){

        if(bindingResult.hasFieldErrors("taskContent"))
            System.out.println(bindingResult);
        else {

            task.setAllAnswers(a + ";" + b + ";" + c + ";" + d);
            task.setRightAnswer(rightAnswer);

            taskRepository.save(task);

        }

        return "redirect:/courses/editSections/editSubsection/editTask?taskId=" + task.getId();
    }

    @GetMapping("/editTask/delete")
    public String deleteTaskFromList(@RequestParam("deleteNumber")int number){

        taskRepository.delete(taskList.get(number));
        taskList.remove(number);

        return getPossibleRedirectValue();
    }
    //endregion

    //region CompileTask methods
    @GetMapping(value = "/editCompileTask")
    public String showCompileTaskForm(@RequestParam("compileTaskId")int compileTaskId,
                                      Model model)
    {

        setListInModel(model);
        model.addAttribute("compileTask",compileTaskRepository.getOne(compileTaskId));

        return compileTaskView;
    }
    @PostMapping("/editCompileTask/edit")
    public String editCompileTaskFromForm(@Valid @ModelAttribute CompileTask compileTask,
                                          BindingResult bindingResult) {

        if(bindingResult.hasErrors())
            System.out.println(bindingResult);
        else
            compileTaskRepository.save(compileTask);

        return "redirect:/courses/editSections/editSubsection/editCompileTask?compileTaskId=" + compileTask.getId();
    }

    @GetMapping(value = "/editCompileTask/delete")
    public String deleteCompileTask(@RequestParam("deleteNumber") int number)
    {
        compileTaskRepository.delete(compileTaskList.get(number));
        compileTaskList.remove(number);

        return getPossibleRedirectValue();
    }

    //endregion


   @PostMapping("/finish")
    public String finishCreating(){

        return "redirect:/courses/";
    }

    private void setListInModel(Model model){

        Hibernate.initialize(editedSubsection.getTaskList());
        Hibernate.initialize(editedSubsection.getTheoryList());
        Hibernate.initialize(editedSubsection.getCompileTaskList());

        taskList = editedSubsection.getTaskList();
        theoryList = editedSubsection.getTheoryList();
        compileTaskList = editedSubsection.getCompileTaskList();

        model.addAttribute("taskList",taskList);
        model.addAttribute("theoryList",theoryList);
        model.addAttribute("compileTaskList",compileTaskList);
    }

    private String getPossibleRedirectValue() {
        String ret = "";

        if(!theoryList.isEmpty()){
            ret = "redirect:/courses/editSections/editSubsection/editContent?theoryId=" + theoryList.get(0).getId();
        }
        else if(!taskList.isEmpty()){
            ret = "redirect:/courses/editSections/editSubsection/editTask?taskId=" + taskList.get(0).getId();
        }else if(!compileTaskList.isEmpty()){
            ret = "redirect:/courses/editSections/editSubsection/editCompileTask?compileTaskId=" + compileTaskList.get(0).getId();
        }
        else{
            // TODO co zrobić jak nie mam już nic do kasowania?
        }

        return ret;
    }

}
