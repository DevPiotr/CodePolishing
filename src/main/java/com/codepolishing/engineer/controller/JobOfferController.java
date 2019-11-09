package com.codepolishing.engineer.controller;

import com.codepolishing.engineer.entity.JobOffer;
import com.codepolishing.engineer.repository.JobOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/jobOffers")
public class JobOfferController {

    @Autowired
    private JobOfferRepository jobOfferRepository;

    @GetMapping("/")
    public String showJobOffer(Model model)
    {
        List<JobOffer> allOffers = jobOfferRepository.findAll();

        model.addAttribute("offers",allOffers);

        return "job_offers";
    }

    @GetMapping("/{id}")
    public String showFullJobOfferContent(@PathVariable(name = "id") int id, Model model)
    {
        JobOffer jobOffer = jobOfferRepository.findById(id);

        model.addAttribute("job",jobOffer);

        return "job_offer_content";
    }

}
