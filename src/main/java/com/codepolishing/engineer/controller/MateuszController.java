package com.codepolishing.engineer.controller;

import com.codepolishing.engineer.entity.User;
import com.codepolishing.engineer.repository.ProvinceRepository;
import com.codepolishing.engineer.repository.UserRepository;
import com.codepolishing.engineer.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class MateuszController {

    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private ProvinceRepository provinceRepository;

    private User user;

    @Autowired
    public MateuszController(UserRepository userRepository, UserRoleRepository userRoleRepository, ProvinceRepository provinceRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.provinceRepository = provinceRepository;
    }

    @GetMapping("/mateusz_main_page")
    public String showMainPage()
    {
        return "mateusz_test";
    }

    @GetMapping("/mateusz_sign_up")
    public String showSignUpPage(User user,Model model)
    {
        model.addAttribute("user", user);
        return "sign_up_user_test";
    }

    @PostMapping("/mateusz_sign_up")
    public String validStarterUserInfo(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model)
    {
        if (bindingResult.hasFieldErrors("email") ||
            bindingResult.hasFieldErrors("password"))
        {
            System.out.println("mateusz_sign_up: Są błedy");

            return "sign_up_user_test";
        }else
        {
            System.out.println("mateusz_sign_up: Nie ma błedów");
            redirectAttributes.addFlashAttribute("user",user);
            return "redirect:/mateusz_sign_up2";
        }
    }
    @GetMapping("mateusz_sign_up2")
    public String showSignUpPage2(@ModelAttribute("user") User user, Model model)
    {
        if(user.getEmail() == null || user.getEmail() == "") {return "redirect:/mateusz_sign_up";}
        model.addAttribute("roles",userRoleRepository.findAll());
        model.addAttribute("provinces",provinceRepository.findAll());
        model.addAttribute("user",user);
        return "sign_up_user_test2";
    }
    @PostMapping("mateusz_sign_up2")
    public String validAllUserInfo(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model)
    {
        if(user.getEmail() == null || user.getEmail() == "") {return "redirect:/mateusz_sign_up";}
        model.addAttribute("roles",userRoleRepository.findAll());
        model.addAttribute("provinces",provinceRepository.findAll());
        model.addAttribute("user",user);
        if(bindingResult.hasErrors())
        {
            System.out.println("mateusz_sign_up2: Są błędy");
            return "sign_up_user_test2";
        }else
        {
            System.out.println("mateusz_sign_up2: Nie ma błędów");

            userRepository.saveAndFlush(user);

            return "redirect:/mateusz_main_page";
        }
    }
}
