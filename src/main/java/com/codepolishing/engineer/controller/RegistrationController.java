package com.codepolishing.engineer.controller;

import com.codepolishing.engineer.entity.User;
import com.codepolishing.engineer.repository.ProvinceRepository;
import com.codepolishing.engineer.repository.UserRepository;
import com.codepolishing.engineer.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.rmi.Naming;


@Controller
public class RegistrationController {

    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private ProvinceRepository provinceRepository;

    private User user; // storage user's registration info

    @Autowired
    public RegistrationController(UserRepository userRepository, UserRoleRepository userRoleRepository, ProvinceRepository provinceRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.provinceRepository = provinceRepository;
    }

    @GetMapping("/index")
    public String showMainPage()
    {
        return "index";
    }

    @GetMapping("/sign_up")
    public String showSignUpPage(User user, Model model)
    {
        this.user = user;
        model.addAttribute("user", user);
        return "user_sign_up";
    }
    @PostMapping("/sign_up")
    public String validStarterUserInfo(@Valid @ModelAttribute("user") User user, BindingResult bindingResult)
    {
        if (bindingResult.hasFieldErrors("email") ||
                bindingResult.hasFieldErrors("password"))
        {
            System.out.println("sign_up: Są błedy");
            return "user_sign_up";
        }else
        {
            System.out.println("sign_up: Nie ma błedów");
            //szyfrowanie hasła
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            this.user = user;
            return "redirect:/sign_up2";
        }
    }
    @GetMapping("sign_up2")
    public String showSignUpPage2(User user, Model model)
    {
        user = this.user;
        model.addAttribute("roles",userRoleRepository.findAll());
        model.addAttribute("provinces",provinceRepository.findAll());
        model.addAttribute("user",user);
        return "user_sign_up2";
    }
    @PostMapping("sign_up2")
    public String validAllUserInfo(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model)
    {
        model.addAttribute("roles",userRoleRepository.findAll());
        model.addAttribute("provinces",provinceRepository.findAll());
        if(bindingResult.hasErrors())
        {
            System.out.println(bindingResult);
            System.out.println("sign_up2: Są błędy");
            return "user_sign_up2";
        }else {
            System.out.println("sign_up2: Nie ma błędów");
            System.out.println(user.toString());
            userRepository.save(user);
            return "redirect:/";
        }

    }
}
