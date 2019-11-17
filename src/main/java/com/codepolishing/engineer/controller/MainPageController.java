package com.codepolishing.engineer.controller;

import com.codepolishing.engineer.entity.User;
import com.codepolishing.engineer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

public class MainPageController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/")
    public String showMainPage(Model model){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal instanceof UserDetails){
            User user = userRepository.findByEmail(((UserDetails) principal).getUsername());
            model.addAttribute("userName",user.getName());
            model.addAttribute("userSurname",user.getSurname());
        }
        return "index";
    }
}
