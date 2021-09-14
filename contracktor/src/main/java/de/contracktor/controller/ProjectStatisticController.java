package de.contracktor.controller;

import de.contracktor.model.DateFormatter;
import de.contracktor.model.Organisation;
import de.contracktor.model.Project;
import de.contracktor.model.Role;
import de.contracktor.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProjectStatisticController {

    @Autowired
    ProjectRepository projectRepository;

    List<Project> selectedProjects = new ArrayList<>();

    List<Project> searchedProjects = new ArrayList<>();

    DateFormatter formatter = new DateFormatter();

    @GetMapping("/project-statistic")
    public String getProjectStatistic(Model model) {

        model.addAttribute("projects", projectRepository.findAll());
        model.addAttribute("selectedProjects", selectedProjects);
        model.addAttribute("formatter", formatter);
        model.addAttribute("filter", "");
        return "project-statistic";
    }

    @PostMapping("/project-statistic/addAll")
    public String getAddAllProjectStatistic(Model model) {
        selectedProjects = projectRepository.findAll();

        model.addAttribute("projects", projectRepository.findAll());
        model.addAttribute("selectedProjects", selectedProjects);
        model.addAttribute("formatter", formatter);
        model.addAttribute("filter", "");
        return "project-statistic";
    }

    @PostMapping("/project-statistic/removeAll")
    public String getRemoveAllProjectStatistic(Model model) {
        selectedProjects = new ArrayList<>();

        model.addAttribute("projects", projectRepository.findAll());
        model.addAttribute("selectedProjects", selectedProjects);
        model.addAttribute("formatter", formatter);
        model.addAttribute("filter", "");
        return "project-statistic";
    }

    @PostMapping("/project-statistic/search")
    public String getSearchAllProjectStatistic(@RequestParam String search, Model model) {
        List<Project> projects = projectRepository.findAll();

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

        model.addAttribute("projects", searchedProjects);
        model.addAttribute("selectedProjects", selectedProjects);
        model.addAttribute("formatter", formatter);
        model.addAttribute("filter", "");
        return "project-statistic";
    }

    @PostMapping("/project-statistic/add")
    public String getAddProjectStatistic(@RequestParam int id, Model model) {
        List<Project> projects = projectRepository.findAll();
        Project project = projectRepository.findByProjectID(id);
        boolean contains = false;
        for (Project p : selectedProjects) {
            if(p.getProjectID() == project.getProjectID()) {
                contains = true;
            }
        }
        if (!contains) {
            selectedProjects.add(project);
        }



        model.addAttribute("projects", projects);
        model.addAttribute("selectedProjects", selectedProjects);
        model.addAttribute("formatter", formatter);
        model.addAttribute("filter", "");
        return "project-statistic";
    }

    @PostMapping("/project-statistic/remove")
    public String getRemoveProjectStatistic(@RequestParam int id, Model model) {
        List<Project> projects = projectRepository.findAll();
        Project project = projectRepository.findByProjectID(id);
        List<Project> newSelected = new ArrayList<>();
        for (Project p : selectedProjects) {
            if(p.getProjectID() != project.getProjectID()) {
                newSelected.add(p);
            }
        }
        selectedProjects = newSelected;




        model.addAttribute("projects", projects);
        model.addAttribute("selectedProjects", selectedProjects);
        model.addAttribute("formatter", formatter);
        model.addAttribute("filter", "");
        return "project-statistic";
    }

    @GetMapping("/project-statistic/{filter}")
    public String getFilteredProjectStatistic(@PathVariable String filter, Model model) {
        List<Project> sortList = searchedProjects;

        if(searchedProjects.isEmpty()) {
            sortList = projectRepository.findAll();
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



        model.addAttribute("projects", sortList);
        model.addAttribute("selectedProjects", selectedProjects);
        model.addAttribute("formatter", formatter);
        model.addAttribute("filter", filter);
        return "project-statistic";
    }
}
