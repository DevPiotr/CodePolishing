package com.codepolishing.engineer.controller;

import com.codepolishing.engineer.entity.Course;
import com.codepolishing.engineer.entity.CourseSection;
import com.codepolishing.engineer.entity.User;
import com.codepolishing.engineer.repository.CourseLevelRepository;
import com.codepolishing.engineer.repository.CourseRepository;
import com.codepolishing.engineer.repository.CourseSectionRepository;
import com.codepolishing.engineer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CourseLevelRepository courseLevelRepository;

    @Autowired
    CourseSectionRepository courseSectionRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public String showCourses(Model model,Principal principal){

        HashMap<String,List<Course>> allCourses = new HashMap<>();

        allCourses.put("basic",courseRepository.findCoursesByIdCourseLevel(1));
        allCourses.put("medium",courseRepository.findCoursesByIdCourseLevel(2));
        allCourses.put("hard",courseRepository.findCoursesByIdCourseLevel(3));

        if(principal != null) {
            User user = userRepository.findByEmail(principal.getName());
            removeCoursesThatUserJoin(user,allCourses);
        }

        model.addAttribute("basicCourses",allCourses.get("basic"));
        model.addAttribute("mediumCourses", allCourses.get("medium"));
        model.addAttribute("hardCourses", allCourses.get("hard"));

        model.addAttribute("courseLevelName", courseLevelRepository.findAll());

        return "courses";
    }

    @GetMapping("/{id}")
    public String showOneCourse(@PathVariable("id")int id, Model model){

        Course course = courseRepository.getOne(id);

        List<CourseSection> courseSectionList = courseSectionRepository.findCourseSectionsByIdCourse(id);

        model.addAttribute("course",course);
        model.addAttribute("courseSections",courseSectionList);

        return "single_course_info";
    }

    @RequestMapping("/{id}/sections")
    public String showCourseSections(@PathVariable("id")int id, Model model){

        List<CourseSection> courseSectionList = courseSectionRepository.findCourseSectionsByIdCourse(id);

        model.addAttribute("courseSections",courseSectionList);

        return "course_sections";
    }

    @RequestMapping("/joinCourse")
    public String joinCourse(@RequestParam("idCourse")int idCourse, Principal principal){

        if(principal != null) {

            User user = userRepository.findByEmail(principal.getName());

            Course courseToJoin = courseRepository.getOne(idCourse);

            user.getCourseList().add(courseToJoin);

            userRepository.saveAndFlush(user);
        }
        return "redirect:/userCourses/";
    }

    @RequestMapping("/joinToSection")
    public String joinToSection(@RequestParam("idCourseSection")int idCourseSection,Principal principal){

        CourseSection courseSectionToJoin;
        if(principal != null){
            User user = userRepository.findByEmail(principal.getName());

            courseSectionToJoin = courseSectionRepository.getOne(idCourseSection);

            if(!user.getCourseSectionList().contains(courseSectionToJoin)) {
                user.getCourseSectionList().add(courseSectionToJoin);

                userRepository.saveAndFlush(user);
            }

            //TODO Rozdziel kontunacje i dołączanie
            return "redirect:/sections/"+idCourseSection+"?idSub="+courseSectionToJoin.getCourseSubsectionList().get(0).getId()+"&part=0"+"&contentType=theory";
        }
        else{
            return "redirect:/courses/";
        }
    }

    private void removeCoursesThatUserJoin(User user,HashMap<String,List<Course>> allCourses){

        List<Course> CoursesJoinedByUser = user.getCourseList();

        for(List<Course> courses : allCourses.values()){
            courses.removeAll(CoursesJoinedByUser);
        }

    }
}
