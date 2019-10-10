package com.codepolishing.engineer.controller;

import com.codepolishing.engineer.entity.CourseSection;
import com.codepolishing.engineer.entity.User;
import com.codepolishing.engineer.repository.CourseLevelRepository;
import com.codepolishing.engineer.repository.CourseRepository;
import com.codepolishing.engineer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/userCourses")
public class UserCourseController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CourseLevelRepository courseLevelRepository;

    @RequestMapping("/")
    public String showUserCourses(Model model, Principal principal){

        User user = userRepository.findByEmail(principal.getName());


        model.addAttribute("basicCoursesThatUserJoined",courseRepository.findCoursesByCourseSectionListInAndIdCourseLevel(getOnlyFirstSectionsOfAllCourses(user.getCourseSectionList()),1));
        model.addAttribute("mediumCoursesThatUserJoined",courseRepository.findCoursesByCourseSectionListInAndIdCourseLevel(getOnlyFirstSectionsOfAllCourses(user.getCourseSectionList()),2));
        model.addAttribute("hardCoursesThatUserJoined",courseRepository.findCoursesByCourseSectionListInAndIdCourseLevel(getOnlyFirstSectionsOfAllCourses(user.getCourseSectionList()),3));

        model.addAttribute("courseLevelName", courseLevelRepository.findAll());

        return "user_courses";
    }

    private List<CourseSection> getOnlyFirstSectionsOfAllCourses(List<CourseSection> courseSectionList) {

        for(CourseSection courseSection: courseSectionList){

            if(courseSection.getSectionPart() != 1){
                courseSectionList.remove(courseSection);
            }
        }
        return courseSectionList;
    }
}
