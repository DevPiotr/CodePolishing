package com.codepolishing.engineer.controller;

import com.codepolishing.engineer.entity.User;
import com.codepolishing.engineer.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Date;


@Controller
public class UserController {

    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
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
}
