package com.codepolishing.engineer.controller;

import com.codepolishing.engineer.entity.User;
import com.codepolishing.engineer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class RankingController {

    @Autowired
    private UserRepository userRepository;


    @RequestMapping("/users-ranking")
    public String showUsersRanking(Model model)
    {
        List<User> allUsers = userRepository.findAllByOrderByScoreDesc();

        model.addAttribute("allUsers", allUsers);

        return "users_ranking";
    }
}
