package com.codepolishing.engineer.controller.Add;

import com.codepolishing.engineer.entity.*;
import com.codepolishing.engineer.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/courses/editSections/addSubsection")
public class AddSubsectionController {

    @Autowired
    CourseSubsectionRepository courseSubsectionRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TheoryRepository theoryRepository;

    @Autowired
    CompileTaskRepository compileTaskRepository;

    private int rememberCourseSectionId;
    private String rememberSubsectionName;
    private int rememberNumberToEdit;

    private ArrayList<Task> taskList;
    private ArrayList<Theory> theoryList;
    private ArrayList<CompileTask> compileTaskList;

    private final String contentView = "subsection_management/add_subsection_content";
    private final String taskView = "subsection_management/add_subsection_task";
    private final String compileTaskView = "subsection_management/add_subsection_compile_task";

    @PostConstruct
    private void postConstructor()
    {
        taskList = new ArrayList<>();
        theoryList = new ArrayList<>();
        compileTaskList = new ArrayList<>();
    }

    @PostMapping("/")
    public String beginCreatingSubsection(@RequestParam("name")String subSectionName){

        return "redirect:/courses/editSections/addSubsection/addContent?sectionId=" + rememberCourseSectionId + "&subSectionName=" + subSectionName;
    }

    @GetMapping("/{id}")
    public String showFormToAddSubsection(@PathVariable("id")int courseSectionId){
        rememberCourseSectionId = courseSectionId;

        return "add_subsection_to_existed_section";
    }

    @GetMapping(value = "/addContent", params = {"sectionId","subSectionName"})
    public String showFormToAddSubsectionContent(@RequestParam("sectionId")int courseSectionId,
                                                 @RequestParam("subSectionName")String subSectionName,
                                                 Theory theory,
                                                 Model model){

        rememberCourseSectionId = courseSectionId;
        rememberSubsectionName = subSectionName;

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

        return "redirect:/courses/editSections/addSubsection/addContent";
    }

    @PostMapping("/addContent/delete")
    public String deleteContent(@RequestParam("deleteNumber")int number){

        if(!theoryList.isEmpty()) {
            theoryList.remove(number);
        }

        return "redirect:/courses/editSections/addSubsection/addContent";
    }
    @GetMapping("/addContent/showEdit")
    public String EditContentFromList(@RequestParam("number")int number,
                                      Model model){
        Theory theory = theoryList.get(number);

        setListInModel(model);

        model.addAttribute("theory",theory);
        model.addAttribute("edit","edit");

        return contentView;
    }

    @GetMapping("/addContent/edit")
    public String changeContentInList(@ModelAttribute("theory")Theory theory){

        theoryList.add(rememberNumberToEdit,theory);

        return "edirect:/courses/editSections/addSubsection/addContent";
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
            taskList.add(task);
        }

        return "redirect:/courses/editSections/addSubsection/addTask";
    }

    @PostMapping("/addTask/delete")
    public String delete(@RequestParam("deleteNumber")int number){

        if(!taskList.isEmpty()) {
            taskList.remove(number);
        }

        return "redirect:/courses/editSections/addSubsection/addTask";
    }
    //endregion

    //region CompileTask methods
    @RequestMapping(value = "/addCompileTask", method = RequestMethod.GET)
    public String showCompileTaskForm(Model model)
    {

        CompileTask compileTask = getSimpleCompileTask();
        setListInModel(model);
        model.addAttribute("compileTask",compileTask);

        return compileTaskView;
    }
    @RequestMapping(value = "/addCompileTask", method = RequestMethod.POST)
    public String addCompileTaskFromForm(@ModelAttribute CompileTask compileTask)
    {
        compileTaskList.add(compileTask);

        return "redirect:/courses/editSections/addSubsection/addCompileTask";
    }
    @RequestMapping(value = "/addCompileTask/delete")
    public String deleteCompileTask(@RequestParam("deleteNumber") int number)
    {
        if(!compileTaskList.isEmpty()) {
            compileTaskList.remove(number);
        }
        return "redirect:/courses/editSections/addSubsection/addCompileTask";
    }


    //endregion


    @PostMapping("/finish")
    public String finishCreating(){


        CourseSubsection newCourseSubsection = saveCourseSubsectionInDatabase();

        if(!theoryList.isEmpty())
            newCourseSubsection.setTheoryList(theoryRepository.saveAll(theoryList));

        if(!taskList.isEmpty())
            newCourseSubsection.setTaskList(taskRepository.saveAll(taskList));

        if(!compileTaskList.isEmpty())
            newCourseSubsection.setCompileTaskList(compileTaskRepository.saveAll(compileTaskList));

        courseSubsectionRepository.save(newCourseSubsection);

        return "redirect:/courses/";
    }

    private CourseSubsection saveCourseSubsectionInDatabase() {
        CourseSubsection courseSubsection = new CourseSubsection();

        courseSubsection.setIdCourseSection(rememberCourseSectionId);
        courseSubsection.setName(rememberSubsectionName);

        return courseSubsectionRepository.save(courseSubsection);
    }

    private void setListInModel(Model model){
            model.addAttribute("taskList",taskList);
            model.addAttribute("theoryList",theoryList);
            model.addAttribute("compileTaskList",compileTaskList);
    }
    private CompileTask getSimpleCompileTask()
    {
        CompileTask compileTask = compileTaskRepository.findById(1);
        compileTask.setId(0);
        return compileTask;
    }

}
