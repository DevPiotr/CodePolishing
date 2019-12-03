package com.codepolishing.engineer.controller;

import com.codepolishing.engineer.entity.User;
import com.codepolishing.engineer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping("/signIn")
    public String userLoginPage()
    {
        return "user_login_page";
    }

    @RequestMapping("/signInError")
    public String userLoginPageError(Model model)
    {
        model.addAttribute("loginError", true);
        return "user_login_page";
    }
    @RequestMapping("/logout")
    public String userLogoutPage()
    {
        return "index";
    }
}
