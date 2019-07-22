package com.codepolishing.engineer.controller;

import com.codepolishing.engineer.entity.JobOffer;
import com.codepolishing.engineer.entity.User;
import com.codepolishing.engineer.repository.JobOfferRepository;
import com.codepolishing.engineer.repository.UserRepository;
import com.codepolishing.engineer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class UserController {

    private UserService userService;

    private JobOfferRepository jobOfferRepository;

    private UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, JobOfferRepository jobOfferRepository, UserRepository userRepository) {
        this.userService = userService;
        this.jobOfferRepository = jobOfferRepository;
        this.userRepository = userRepository;
    }

    @RequestMapping("/test")
    public @ResponseBody String test(){
        JobOffer jobOffer = jobOfferRepository.getOne(3);

        User user = userRepository.getOne(1);

        jobOffer.getUserList().add(user);

        try{
            userService.save(user);
        }catch(DataIntegrityViolationException e){
            System.out.println("Duplicate!");
        }

        return "hello";
    }

}
