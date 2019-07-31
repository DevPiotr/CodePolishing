package com.codepolishing.engineer.controller;


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

        User user =  new User();

        user.setIdUserRole(1);
        user.setIdProvince(1);
        user.setScore(1);
        user.setPassword("passw");
        user.setName("stachu");
        user.setSurname("motyka");
        user.setCity("wałbżucy");
        user.setHouseNumber("10a");
        user.setPostCode("10-122");
        user.setEmail("faosda@o.pl");
        user.setBirthDate(new Date(System.currentTimeMillis()));

        try{
            userRepository.save(user);
        }catch(DataIntegrityViolationException e) {
            System.out.println("Duplicate!");
        }

        return "hello";
    }

    @RequestMapping("/")
    public String showIndexPage() {
        return "index_test";
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
