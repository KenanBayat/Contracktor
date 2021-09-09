package de.contracktor.controller;

import de.contracktor.controller.dato.ProjectsDato;
import de.contracktor.model.Organisation;
import de.contracktor.model.Project;
import de.contracktor.model.State;
import de.contracktor.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Controller
public class ProjectController {

    //@Autowired
    //ProjectRepository projectRepository;

    @GetMapping("/projects")
    public String getProjectList(Model model) {
        //model.addAttribute("projects",projectRepository.findAll());

        ProjectsDato projects = new ProjectsDato();

        model.addAttribute("projects", projects);
        return "project-list";
    }

    @GetMapping("/project/{projectId}/details")
    public String getProjectDetails(@PathVariable int projectId, Model model) {
        //model.addAttribute("project", projectRepository.findById(projectId).get());
        ProjectsDato projects = new ProjectsDato();

        model.addAttribute("project", projects.findById(projectId));
        return "project-details";
    }
}
