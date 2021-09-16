package de.contracktor.controller;

import de.contracktor.DatabaseService;
import de.contracktor.UserManager;
import de.contracktor.model.*;
import de.contracktor.repository.BillingItemRepository;
import de.contracktor.repository.BillingUnitRepository;
import de.contracktor.repository.ContractRepository;
import de.contracktor.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class ProjectStatisticController {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ContractRepository contractRepository;

    @Autowired
    BillingUnitRepository billingUnitRepository;

    @Autowired
    BillingItemRepository billingItemRepository;

    @Autowired
    DatabaseService databaseService;

    @Autowired
    UserManager userManager;

    List<Project> selectedProjects = new ArrayList<>();

    List<Project> searchedProjects = new ArrayList<>();

    DateFormatter formatter = new DateFormatter();

    @GetMapping("/project-statistic")
    public String getProjectStatistic(Model model) {

        model.addAttribute("userManager", userManager);
        model.addAttribute("labels", getLabels());
        model.addAttribute("count", getCount());
        model.addAttribute("projects", databaseService.getAllProjects());
        model.addAttribute("selectedProjects", selectedProjects);
        model.addAttribute("formatter", formatter);
        model.addAttribute("filter", "");
        return "project-statistic";
    }

    @PostMapping("/project-statistic/addAll")
    public String getAddAllProjectStatistic(Model model) {
        selectedProjects = databaseService.getAllProjects();

        model.addAttribute("userManager", userManager);
        model.addAttribute("labels", getLabels());
        model.addAttribute("count", getCount());
        model.addAttribute("projects", databaseService.getAllProjects());
        model.addAttribute("selectedProjects", selectedProjects);
        model.addAttribute("formatter", formatter);
        model.addAttribute("filter", "");
        return "project-statistic";
    }

    @PostMapping("/project-statistic/removeAll")
    public String getRemoveAllProjectStatistic(Model model) {
        selectedProjects = new ArrayList<>();

        model.addAttribute("userManager", userManager);
        model.addAttribute("labels", getLabels());
        model.addAttribute("count", getCount());
        model.addAttribute("projects", databaseService.getAllProjects());
        model.addAttribute("selectedProjects", selectedProjects);
        model.addAttribute("formatter", formatter);
        model.addAttribute("filter", "");
        return "project-statistic";
    }

    @PostMapping("/project-statistic/search")
    public String getSearchAllProjectStatistic(@RequestParam String search, Model model) {
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
        model.addAttribute("labels", getLabels());
        model.addAttribute("count", getCount());
        model.addAttribute("projects", searchedProjects);
        model.addAttribute("selectedProjects", selectedProjects);
        model.addAttribute("formatter", formatter);
        model.addAttribute("filter", "");
        return "project-statistic";
    }

    @PostMapping("/project-statistic/generate")
    public String getStatistic(@RequestParam int id, Model model) {
        selectedProjects = List.of(databaseService.getProjectByProjectID(id));

        model.addAttribute("userManager", userManager);
        model.addAttribute("labels", getLabels());
        model.addAttribute("count", getCount());
        model.addAttribute("projects", databaseService.getAllProjects());
        model.addAttribute("selectedProjects", selectedProjects);
        model.addAttribute("formatter", formatter);
        model.addAttribute("filter", "");
        return "project-statistic";
    }

    @PostMapping("/project-statistic/add")
    public String getAddProjectStatistic(@RequestParam int id, Model model) {

        List<Project> projects = databaseService.getAllProjects();
        Project project = databaseService.getProjectByProjectID(id);
        boolean contains = false;
        for (Project p : selectedProjects) {
            if(p.getProjectID() == project.getProjectID()) {
                contains = true;
            }
        }
        if (!contains) {
            selectedProjects.add(project);
        }

        model.addAttribute("userManager", userManager);
        model.addAttribute("labels", getLabels());
        model.addAttribute("count", getCount());
        model.addAttribute("projects", projects);
        model.addAttribute("selectedProjects", selectedProjects);
        model.addAttribute("formatter", formatter);
        model.addAttribute("filter", "");
        return "project-statistic";
    }

    @PostMapping("/project-statistic/remove")
    public String getRemoveProjectStatistic(@RequestParam int id, Model model) {
        List<Project> projects = databaseService.getAllProjects();
        Project project = databaseService.getProjectByProjectID(id);
        List<Project> newSelected = new ArrayList<>();
        for (Project p : selectedProjects) {
            if(p.getProjectID() != project.getProjectID()) {
                newSelected.add(p);
            }
        }
        selectedProjects = newSelected;



        model.addAttribute("userManager", userManager);
        model.addAttribute("labels", getLabels());
        model.addAttribute("count", getCount());
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
        model.addAttribute("labels", getLabels());
        model.addAttribute("count", getCount());
        model.addAttribute("projects", sortList);
        model.addAttribute("selectedProjects", selectedProjects);
        model.addAttribute("formatter", formatter);
        model.addAttribute("filter", filter);
        return "project-statistic";
    }

    public List<String> getLabels() {
        List<String> labels = new ArrayList<>();
        List<BillingItem> selectedBillingItems = new ArrayList<>();
        for(Project p : selectedProjects) {
            selectedBillingItems.addAll(databaseService.getAllBillingItemsOfProject(p));
        }
        for (BillingItem b: selectedBillingItems) {
            labels.add(b.getStatus().getStateName());
        }
        Set<String> set = new HashSet<>(labels);
        labels.clear();
        labels.addAll(set);
        return labels;
    }

    public List<Integer> getCount() {
        List<BillingItem> selectedBillingItems = new ArrayList<>();
        for(Project p : selectedProjects) {
            selectedBillingItems.addAll(databaseService.getAllBillingItemsOfProject(p));
        }
        List<String> labels = getLabels();
        List<Integer> count = new ArrayList<>();
        for(String s : labels) {
            int counter = 0;
            for (BillingItem b : selectedBillingItems) {
                if (b.getStatus().getStateName().equals(s)) {
                    counter++;
                }
            }
            count.add(counter);
        }
        return count;
    }

}
