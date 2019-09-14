package com.codepolishing.engineer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/signIn")
    public String userLoginPage()
    {
        return  "user_login_page";
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
