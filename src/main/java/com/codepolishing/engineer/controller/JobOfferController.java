package com.codepolishing.engineer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/jobOffers")
public class JobOfferController {

    @GetMapping("/")
    public String showJobOffer()
    {
        return "job_offers";
    }

}
