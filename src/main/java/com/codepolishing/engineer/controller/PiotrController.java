package com.codepolishing.engineer.controller;

import com.codepolishing.engineer.entity.User;
import com.codepolishing.engineer.repository.ProvinceRepository;
import com.codepolishing.engineer.repository.UserRepository;
import com.codepolishing.engineer.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class PiotrController {

    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;

    @Autowired
    public PiotrController(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @GetMapping("/login")
    public String logInUser(){
        return "piotr_login";
    }

    @PostMapping("/login")
    public String checkIfUserExist(@RequestParam("email")String email,@RequestParam("password")String password, RedirectAttributes redirectAttributes,Model model){

        User user = userRepository.findByEmail(email);
        String message = "";

        if(user == null)
            message = "Nie znaleziono użytkownika";
        else if(!user.getPassword().equals(password))
            message = "Błędne hasło";

        if(!message.isEmpty()){
            model.addAttribute("message",message);
            //redirectAttributes.addFlashAttribute("message",message);
            return "piotr_login";
        }
        else{
            model.addAttribute("user",user);
            return "piotr_userLoged";
        }
    }

}
