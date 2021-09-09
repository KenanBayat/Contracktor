package de.contracktor.controller;

import de.contracktor.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProjectController {

    @Autowired
    ProjectRepository projectRepository;

    @GetMapping("/projects")
    public String getProjectList(Model model) {
        //model.addAttribute("projects",projectRepository.findAll());
        List<Project> projects = List.of(
                new Project(1, 27, "Hausbau");
                new Project(2,42, "Bahnhof");
                new Project(3,13, "Berliner Flughafen");
        )
        model.addAttribute("projects", projects);
        return "project-list";
    }

    @GetMapping("/project-details/{projectId}")
    public String getProjectDetails(@PathVariable int projectId, Model model) {
        model.addAttribute("project", projectRepository.findById(projectId).get());
        return "project-details";
    }
}
