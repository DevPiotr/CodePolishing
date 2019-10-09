package com.codepolishing.engineer.controller;

import com.codepolishing.engineer.entity.Course;
import com.codepolishing.engineer.entity.CourseSection;
import com.codepolishing.engineer.entity.User;
import com.codepolishing.engineer.repository.CourseRepository;
import com.codepolishing.engineer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/UserCourses")
public class UserCourseController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CourseRepository courseRepository;

    public String showUserCourses(Model model, Principal principal){

        User user = userRepository.findByEmail(principal.getName());

        List<Course> ListOfCoursesThatUserJoined = courseRepository.findCoursesByCourseSectionList(getOnlyFirstSectionsOfAllCourses(user.getCourseSectionList()));

        model.addAttribute("coursesThatUserJoined",ListOfCoursesThatUserJoined);

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
