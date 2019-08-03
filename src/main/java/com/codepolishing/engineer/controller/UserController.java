package com.codepolishing.engineer.controller;

import com.codepolishing.engineer.entity.Course;

import com.codepolishing.engineer.entity.User;
import com.codepolishing.engineer.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;


@Controller
public class UserController {

    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private ProvinceRepository provinceRepository;

    @Autowired
    public UserController(UserRepository userRepository, UserRoleRepository userRoleRepository, ProvinceRepository provinceRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.provinceRepository = provinceRepository;
    }

    @RequestMapping("/test")
    public @ResponseBody String test(){

        User user = User.builder()
                .idUserRole(1)
                .idProvince(1)
                .score(1)
                .password("passw")
                .name("stachu")
                .surname("motyka")
                .city("wałbżucy")
                .houseNumber("10a")
                .postCode("10-122")
                .email("faosda@o.pl")
                .birthDate(new Date(System.currentTimeMillis()))
                .build();

        try{
            userRepository.save(user);
        }catch(DataIntegrityViolationException e) {
            System.out.println("Duplicate!");
        }

        return "hello";
    }

    @RequestMapping("/index.html")
    public String showIndexPage() {
        return "maintmp";
    }

    @RequestMapping("/maintmp")
    public String showIndex2Page() {
        return "index";
    }

    @GetMapping("/signUp")
    public String signUpNewUser(User user){
        return "sign_up_user";
    }

    @PostMapping("/moreInfo")
    public String addMoreUserInfo(User user, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        String message = "";

        if(user.getEmail().trim().equals(""))
            message = "Email jest wymagany";

        if(!user.getPassword().equals(user.getConfirmPassword()))
            message += "\n Hasło musi się zgadzać";

        if(!message.isEmpty()){
            redirectAttributes.addFlashAttribute("message",message);
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");

            return "redirect:/signUp";
        }
        else{
            model.addAttribute("user",user);
            model.addAttribute("roles",userRoleRepository.findAll());
            model.addAttribute("provinces",provinceRepository.findAll());
            return "get_more_info";
        }

    }





}
