package de.contracktor.controller;
import de.contracktor.UserManager;
import de.contracktor.repository.OrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class PageController {


    @Autowired
    UserManager userManager;

    @Autowired
    OrganisationRepository organisationRepository;
    //TODO Remove

    @GetMapping("/")
    public String getLandingPage() {
        return "landing";
    }

    @GetMapping("/statistic")
    public String generateStatistic() {
        return "statistic";
    }

}
