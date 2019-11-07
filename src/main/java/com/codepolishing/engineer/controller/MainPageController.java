package com.codepolishing.engineer.controller;

import org.springframework.web.bind.annotation.RequestMapping;

public class MainPageController {

    @RequestMapping("/")
    public String showMainPage(){
        return "index";
    }

}
