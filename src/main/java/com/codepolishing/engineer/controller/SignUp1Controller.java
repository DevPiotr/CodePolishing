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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
public class SignUp1Controller {

    @GetMapping("/sign_up")
    public String showSignUpPage(User user, Model model) {
        model.addAttribute("user", user);
        return "user_sign_up";
    }

    @PostMapping("/sign_up")
    public String validStarterUserInfo(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasFieldErrors("email") ||
                bindingResult.hasFieldErrors("password")) {
            return "user_sign_up";
        } else {
            //szyfrowanie hasła
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            user.setConfirmPassword(user.getPassword());
            redirectAttributes.addFlashAttribute(user);
            return "redirect:/sign_up2";
        }
    }
}
