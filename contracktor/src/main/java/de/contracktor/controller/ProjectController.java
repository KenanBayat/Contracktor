package de.contracktor.controller;

import de.contracktor.model.Contract;
import de.contracktor.model.CurrencyFormatter;
import de.contracktor.model.DateFormatter;
import de.contracktor.model.Project;
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

    //connection of the database
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    ContractRepository contractRepository;

    /**Function:
     * - List of all projects
     * - view details of a certain project
    */
    @GetMapping("/projects")
    public String getProjectList(Model model) {
        model.addAttribute("projects", projectRepository.findAll());
        return "project-list";
    }

    /** Function
     * -view the project-details
     * projectId : ID of the specific project
     */
    @GetMapping("/project/{projectId}/details")
    public String getProjectDetails(@PathVariable int projectId, Model model) {

        Project project = projectRepository.findByProjectID(projectId);
        List<Contract> contracts = contractRepository.findByProject(project);

        model.addAttribute("project", project);
        model.addAttribute("contracts", contracts);
        model.addAttribute("price", CurrencyFormatter.format(project.getTotalPrice()));
        model.addAttribute("creation", DateFormatter.format(project.getCreationDate()));
        model.addAttribute("completion", DateFormatter.format(project.getCompletionDate()));
        return "project-details";
    }
}
