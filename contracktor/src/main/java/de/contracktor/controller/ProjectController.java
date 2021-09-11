package de.contracktor.controller;

import de.contracktor.model.Contract;
import de.contracktor.repository.ContractRepository;
import de.contracktor.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProjectController {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ContractRepository contractRepository;

    @GetMapping("/projects")
    public String getProjectList(Model model) {
        model.addAttribute("projects", projectRepository.findAll());
        return "project-list";
    }

    @GetMapping("/project/{projectId}/details")
    public String getProjectDetails(@PathVariable int projectId, Model model) {
        List<Contract> contractList = (List<Contract>) contractRepository.findAll();
        contractList = contractList.stream().filter(contract -> contract.getProjectId() == projectId).collect(Collectors.toList());


        model.addAttribute("project", projectRepository.findById(projectId));
        model.addAttribute("contracts", contractList);
        return "project-details";
    }
}
