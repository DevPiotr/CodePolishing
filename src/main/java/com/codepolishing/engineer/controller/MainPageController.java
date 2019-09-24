package com.codepolishing.engineer.controller;

import org.springframework.web.bind.annotation.RequestMapping;

public class MainPageController {

    @RequestMapping(value = "/index")
    public String showMainPage() {
        return "index";
    }
}
