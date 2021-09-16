package de.contracktor.controller;

import de.contracktor.DatabaseService;
import de.contracktor.UserManager;
import de.contracktor.model.Contract;
import de.contracktor.model.DateFormatter;
import de.contracktor.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;


@Controller
public class ProjectController {

    //connection of the database
    @Autowired
    DatabaseService databaseService;

    @Autowired
    UserManager userManager;

    List<Project> searchedProjects = new ArrayList<>();

    Project selectedProject = null;

    DateFormatter formatter = new DateFormatter();

    @GetMapping("/projects")
    public String getProjects(Model model) {

        model.addAttribute("userManager", userManager);
        model.addAttribute("projects", databaseService.getAllProjects());
        model.addAttribute("formatter", formatter);
        model.addAttribute("filter", "");
        return "projects";
    }

    @SuppressWarnings("static-access")
	@PostMapping("/projects/search")
    public String getSearchProjects(@RequestParam String search, Model model) {
        List<Project> projects = databaseService.getAllProjects();

        String finalSearch = search.toLowerCase();
        searchedProjects = projects.stream().filter(project -> project.getName().toLowerCase().contains(finalSearch)
                        | project.getAddress().getCity().toLowerCase().contains(finalSearch)
                        | project.getAddress().getCountry().toLowerCase().contains(finalSearch)
                        | project.getAddress().getZipCode().toLowerCase().contains(finalSearch)
                        | project.getAddress().getStreet().toLowerCase().contains(finalSearch)
                        | formatter.format(project.getCompletionDate()).toLowerCase().contains(finalSearch)
                        | formatter.format(project.getCreationDate()).toLowerCase().contains(finalSearch)
                        | project.getDescription().toLowerCase().contains(finalSearch)
                        | project.getOwner().getOrganisationName().toLowerCase().contains(finalSearch)
                        | project.getCreator().toLowerCase().contains(finalSearch)
                        | project.getStatus().getStateName().toLowerCase().contains(finalSearch)
                )
                .collect(Collectors.toList());

        model.addAttribute("userManager", userManager);
        model.addAttribute("projects", searchedProjects);
        model.addAttribute("formatter", formatter);
        model.addAttribute("filter", "");
        return "projects";
    }

    @GetMapping("/projects/{filter}")
    public String getFilteredProjects(@PathVariable String filter, Model model) {
        List<Project> sortList = searchedProjects;

        if(searchedProjects.isEmpty()) {
            sortList = databaseService.getAllProjects();
        }
        if(filter.equals("name_asc")) {
            sortList = sortList.stream().sorted(Comparator.comparing(Project::getLowerName)).collect(Collectors.toList());
        }
        if(filter.equals("name_desc")) {
            sortList = sortList.stream().sorted(Comparator.comparing(Project::getLowerName)).collect(Collectors.toList());
            Collections.reverse(sortList);
        }
        if(filter.equals("city_asc")) {
            sortList = sortList.stream().sorted(Comparator.comparing(Project::getLowerCity)).collect(Collectors.toList());
        }
        if(filter.equals("city_desc")) {
            sortList = sortList.stream().sorted(Comparator.comparing(Project::getLowerCity)).collect(Collectors.toList());
            Collections.reverse(sortList);
        }
        if(filter.equals("owner_asc")) {
            sortList = sortList.stream().sorted(Comparator.comparing(Project::getLowerOwnerName)).collect(Collectors.toList());
        }
        if(filter.equals("owner_desc")) {
            sortList = sortList.stream().sorted(Comparator.comparing(Project::getLowerOwnerName)).collect(Collectors.toList());
            Collections.reverse(sortList);
        }
        if(filter.equals("creator_asc")) {
            sortList = sortList.stream().sorted(Comparator.comparing(Project::getLowerCreator)).collect(Collectors.toList());
        }
        if(filter.equals("creator_desc")) {
            sortList = sortList.stream().sorted(Comparator.comparing(Project::getLowerCreator)).collect(Collectors.toList());
            Collections.reverse(sortList);
        }
        if(filter.equals("status_asc")) {
            sortList = sortList.stream().sorted(Comparator.comparing(Project::getLowerStatus)).collect(Collectors.toList());
        }
        if(filter.equals("status_desc")) {
            sortList = sortList.stream().sorted(Comparator.comparing(Project::getLowerStatus)).collect(Collectors.toList());
            Collections.reverse(sortList);
        }
        if(filter.equals("price_asc")) {
            sortList = sortList.stream().sorted(Comparator.comparing(Project::getTotalPrice)).collect(Collectors.toList());
        }
        if(filter.equals("price_desc")) {
            sortList = sortList.stream().sorted(Comparator.comparing(Project::getTotalPrice)).collect(Collectors.toList());
            Collections.reverse(sortList);
        }
        if(filter.equals("creation_asc")) {
            sortList = sortList.stream().sorted(Comparator.comparing(Project::getCreationDateFormatted)).collect(Collectors.toList());
        }
        if(filter.equals("creation_desc")) {
            sortList = sortList.stream().sorted(Comparator.comparing(Project::getCreationDateFormatted)).collect(Collectors.toList());
            Collections.reverse(sortList);
        }
        if(filter.equals("completion_asc")) {
            sortList = sortList.stream().sorted(Comparator.comparing(Project::getCompletionDateFormatted)).collect(Collectors.toList());
        }
        if(filter.equals("completion_desc")) {
            sortList = sortList.stream().sorted(Comparator.comparing(Project::getCompletionDateFormatted)).collect(Collectors.toList());
            Collections.reverse(sortList);
        }



        model.addAttribute("userManager", userManager);
        model.addAttribute("projects", sortList);
        model.addAttribute("formatter", formatter);
        model.addAttribute("filter", filter);
        return "projects";
    }


    @GetMapping("/project")
    public String getProjectDetails(Model model) {
        Project project = selectedProject;
        DateFormatter formatter = new DateFormatter();
        @SuppressWarnings("static-access")
		String creationDate = formatter.format(project.getCreationDate());
        @SuppressWarnings("static-access")
		String completionDate = formatter.format(project.getCompletionDate());
        model.addAttribute("userManager", userManager);
        model.addAttribute("project", project);
        model.addAttribute("creationDate", creationDate);
        model.addAttribute("completionDate", completionDate);
        return "project";
    }

    @GetMapping("/project/contract")
    public String getContractsOfProject(Model model) {
        Project project = selectedProject;
        List<Contract> contracts = databaseService.getAllContracts();
        List<Contract> selectedContracts = new ArrayList<>();
            for (Contract c : contracts) {
                if(project.getProjectID() == c.getProject().getProjectID()) {
                    selectedContracts.add(c);
                }
            }
        model.addAttribute("userManager", userManager);
        model.addAttribute("project", project);
        model.addAttribute("contracts", selectedContracts);
        return "contractsOfProject";
    }

    @PostMapping("/project")
    public String getProjectDetails(@RequestParam int id, Model model) {
        Project project = databaseService.getProjectByID(id);
        selectedProject = project;
        DateFormatter formatter = new DateFormatter();
        @SuppressWarnings("static-access")
		String creationDate = formatter.format(project.getCreationDate());
        @SuppressWarnings("static-access")
		String completionDate = formatter.format(project.getCompletionDate());
        model.addAttribute("userManager", userManager);
        model.addAttribute("project", project);
        model.addAttribute("creationDate", creationDate);
        model.addAttribute("completionDate", completionDate);
        return "project";
    }
}
