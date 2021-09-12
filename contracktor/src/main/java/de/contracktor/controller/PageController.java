package de.contracktor.controller;

import de.contracktor.model.Organisation;
import de.contracktor.model.Project;
import de.contracktor.model.UserAccount;
import de.contracktor.model.Role;

import de.contracktor.UserManager;
import de.contracktor.repository.OrganisationRepository;
import de.contracktor.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PageController {

    @Autowired
    ProjectRepository projectRepository;

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

    @GetMapping("/projects")
    public String getProjectList(Model model) {
        List<Project> projects = projectRepository.findAll();

        model.addAttribute("projects", projects);
        return "project-list";
    }

    @GetMapping("/project-details")
    public String getProjectDetails(@RequestParam(value = "projectId") String projectId, Model model) {
        model.addAttribute("projectId", projectId);
        return "project-details";
    }

    @GetMapping("/contracts")
    public String getContractList() {
        return "contract-list";
    }

    @GetMapping("/contract-details")
    public String getContractDetails(@RequestParam(value = "contractId") String contractId, Model model) {
        model.addAttribute("contractId", contractId);
        return "contract-details";
    }

    @GetMapping("/billingitems")
    public String getBillingItems() {
        return "billingitem-list";
    }

    @GetMapping("/billingitem-details")
    public String getBillingitemDetails(@RequestParam(value = "billingitemId") String billingitemId, Model model) {
        model.addAttribute("billingitemId", billingitemId);
        return "billingitem-details";
    }
}
