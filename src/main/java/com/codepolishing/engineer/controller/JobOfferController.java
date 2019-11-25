package com.codepolishing.engineer.controller;

import com.codepolishing.engineer.entity.JobOffer;
import com.codepolishing.engineer.repository.JobOfferRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.DateUtils;

import javax.annotation.PostConstruct;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/jobOffers")
public class JobOfferController {

    @Autowired
    private JobOfferRepository jobOfferRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET )
    public String showJobOffer(Model model)
    {
        List<JobOffer> allOffers = jobOfferRepository.findAll();

        model.addAttribute("offers",allOffers);

        return "job_offers";
    }

    @RequestMapping(value = "/{id}/view", method = RequestMethod.GET)
    public String showFullJobOfferContent(@PathVariable int id, Model model)
    {
        JobOffer jobOffer = jobOfferRepository.findById(id);

        model.addAttribute("job",jobOffer);

        return "job_offer_content";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String showJobOfferForm(Model model)
    {
        model.addAttribute("ref","add");
        model.addAttribute("job", new JobOffer());
        return "job_offer_edit";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addJobOffer(Model model, @ModelAttribute("job") JobOffer jobOffer)
    {
        jobOffer.setCreateDate(new Date(System.currentTimeMillis()));
        jobOfferRepository.saveAndFlush(jobOffer);

        model.addAttribute("ref","add");
        model.addAttribute("job",jobOffer);
        model.addAttribute("submit", "Dodano pomyślnie");
        return "job_offer_edit";
    }
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String showJobOfferToEdit(Model model, @PathVariable int id)
    {
        model.addAttribute("job",jobOfferRepository.findById(id));
        model.addAttribute("ref",id+"/edit");
        return "job_offer_edit";
    }
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.POST)
    public String updateJobOffer(Model model, @ModelAttribute JobOffer job, @PathVariable int id)
    {
        model.addAttribute("job",job);
        model.addAttribute("ref",id+"/edit");
        model.addAttribute("submit", "Zaktualizowano pomyślnie");

        job.setModificationDate(new Date(System.currentTimeMillis()));
        jobOfferRepository.save(job);

        return "job_offer_edit";
    }

}
