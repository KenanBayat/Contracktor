package de.contracktor.controller;

import de.contracktor.DatabaseService;
import de.contracktor.UserManager;
import de.contracktor.model.BillingItem;
import de.contracktor.model.Contract;
import de.contracktor.repository.BillingItemRepository;
import de.contracktor.repository.BillingUnitRepository;
import de.contracktor.repository.ContractRepository;
import de.contracktor.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class ContractStatisticController {

    @Autowired
    ContractRepository contractRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    BillingUnitRepository billingUnitRepository;

    @Autowired
    BillingItemRepository billingItemRepository;

    @Autowired
    UserManager userManager;

    @Autowired
    DatabaseService databaseService;

    List<Contract> selectedContracts = new ArrayList<>();

    List<Contract> searchedContracts = new ArrayList<>();

    @GetMapping("/contract-statistic")
    public String getContractStatistic(Model model) {

        model.addAttribute("userManager", userManager);
        model.addAttribute("labels", getContractLabels());
        model.addAttribute("count", getContractCount());
        model.addAttribute("contracts", databaseService.getAllContracts());
        model.addAttribute("selectedContracts", selectedContracts);
        model.addAttribute("filter", "");

        return "contract-statistic";
    }

    @PostMapping("/contract-statistic/addAll")
    public String getAddAllContractStatistic(Model model) {
        selectedContracts = databaseService.getAllContracts();

        model.addAttribute("userManager", userManager);
        model.addAttribute("labels", getContractLabels());
        model.addAttribute("count", getContractCount());
        model.addAttribute("contracts", databaseService.getAllContracts());
        model.addAttribute("selectedContracts", selectedContracts);
        model.addAttribute("filter", "");

        return "contract-statistic";
    }

    @PostMapping("/contract-statistic/removeAll")
    public String getRemoveAllContractStatistic(Model model) {
        selectedContracts = new ArrayList<>();

        model.addAttribute("userManager", userManager);
        model.addAttribute("labels", getContractLabels());
        model.addAttribute("count", getContractCount());
        model.addAttribute("contracts", databaseService.getAllContracts());
        model.addAttribute("selectedContracts", selectedContracts);
        model.addAttribute("filter", "");

        return "contract-statistic";
    }

    @SuppressWarnings("unused")
	@PostMapping("/contract-statistic/remove")
    public String getRemoveProjectStatistic(@RequestParam int id, Model model) {
        List<Contract> contracts = databaseService.getAllContracts();
        Contract contract = contractRepository.findByContractID(id);
        List<Contract> newSelected = new ArrayList<>();
        for (Contract c : selectedContracts) {
            if(c.getContractID() != contract.getContractID()) {
                newSelected.add(c);
            }
        }
        selectedContracts = newSelected;

        model.addAttribute("userManager", userManager);
        model.addAttribute("labels", getContractLabels());
        model.addAttribute("count", getContractCount());
        model.addAttribute("contracts", databaseService.getAllContracts());
        model.addAttribute("selectedContracts", selectedContracts);
        model.addAttribute("filter", "");
        return "contract-statistic";
    }

    @PostMapping("/contract-statistic/add")
    public String getAddProjectStatistic(@RequestParam int id, Model model) {
        Contract contract = contractRepository.findByContractID(id);
        boolean contains = false;
        for (Contract c : selectedContracts) {
            if(c.getContractID() == contract.getContractID()) {
                contains = true;
            }
        }
        if (!contains) {
            selectedContracts.add(contract);
        }

        model.addAttribute("userManager", userManager);
        model.addAttribute("labels", getContractLabels());
        model.addAttribute("count", getContractCount());
        model.addAttribute("contracts", databaseService.getAllContracts());
        model.addAttribute("selectedContracts", selectedContracts);
        model.addAttribute("filter", "");
        return "contract-statistic";
    }

    @PostMapping("/contract-statistic/search")
    public String getSearchAllProjectStatistic(@RequestParam String search, Model model) {
        List<Contract> contracts = databaseService.getAllContracts();

        String finalSearch = search.toLowerCase();
        searchedContracts = contracts.stream().filter(contract -> contract.getName().toLowerCase().contains(finalSearch)
                        | contract.getProject().getName().toLowerCase().contains(finalSearch)
                        | contract.getConsignee().toLowerCase().contains(finalSearch)
                        | contract.getContractor().toLowerCase().contains(finalSearch)
                        | contract.getStatus().getStateName().toLowerCase().contains(finalSearch)
                )
                .collect(Collectors.toList());

        model.addAttribute("userManager", userManager);
        model.addAttribute("labels", getContractLabels());
        model.addAttribute("count", getContractCount());
        model.addAttribute("contracts", searchedContracts);
        model.addAttribute("selectedContracts", selectedContracts);
        model.addAttribute("filter", "");
        return "contract-statistic";
    }

    @GetMapping("/contract-statistic/{filter}")
    public String getFilteredContracts(@PathVariable String filter, Model model) {
        List<Contract> sortList = searchedContracts;

        if(searchedContracts.isEmpty()) {
            sortList = databaseService.getAllContracts();
        }
        if(filter.equals("name_asc")) {
            sortList = sortList.stream().sorted(Comparator.comparing(Contract::getLowerName)).collect(Collectors.toList());
        }
        if(filter.equals("name_desc")) {
            sortList = sortList.stream().sorted(Comparator.comparing(Contract::getLowerName)).collect(Collectors.toList());
            Collections.reverse(sortList);
        }
        if(filter.equals("project_asc")) {
            sortList = sortList.stream().sorted(Comparator.comparing(Contract::getLowerProject)).collect(Collectors.toList());
        }
        if(filter.equals("project_desc")) {
            sortList = sortList.stream().sorted(Comparator.comparing(Contract::getLowerProject)).collect(Collectors.toList());
            Collections.reverse(sortList);
        }
        if(filter.equals("consignee_asc")) {
            sortList = sortList.stream().sorted(Comparator.comparing(Contract::getLowerConsignee)).collect(Collectors.toList());
        }
        if(filter.equals("consignee_desc")) {
            sortList = sortList.stream().sorted(Comparator.comparing(Contract::getLowerConsignee)).collect(Collectors.toList());
            Collections.reverse(sortList);
        }
        if(filter.equals("contractor_asc")) {
            sortList = sortList.stream().sorted(Comparator.comparing(Contract::getLowerContractor)).collect(Collectors.toList());
        }
        if(filter.equals("contractor_desc")) {
            sortList = sortList.stream().sorted(Comparator.comparing(Contract::getLowerContractor)).collect(Collectors.toList());
            Collections.reverse(sortList);
        }
        if(filter.equals("status_asc")) {
            sortList = sortList.stream().sorted(Comparator.comparing(Contract::getLowerStatus)).collect(Collectors.toList());
        }
        if(filter.equals("status_desc")) {
            sortList = sortList.stream().sorted(Comparator.comparing(Contract::getLowerStatus)).collect(Collectors.toList());
            Collections.reverse(sortList);
        }

        model.addAttribute("userManager", userManager);
        model.addAttribute("labels", getContractLabels());
        model.addAttribute("count", getContractCount());
        model.addAttribute("contracts", sortList);
        model.addAttribute("selectedContracts", selectedContracts);
        model.addAttribute("filter", filter);

        return "contract-statistic";
    }

    @PostMapping("/contract-statistic/generate")
    public String getGenerateContractStatistic(@RequestParam int id, Model model) {
        selectedContracts = List.of(contractRepository.findByContractID(id));

        model.addAttribute("userManager", userManager);
        model.addAttribute("labels", getContractLabels());
        model.addAttribute("count", getContractCount());
        model.addAttribute("contracts", databaseService.getAllContracts());
        model.addAttribute("selectedContracts", selectedContracts);
        model.addAttribute("filter", "");

        return "redirect:/contract-statistic";
    }

    public List<String> getContractLabels() {
        List<String> labels = new ArrayList<>();
        List<BillingItem> selectedBillingItems = new ArrayList<>();
        for(Contract c : selectedContracts) {
            selectedBillingItems.addAll(databaseService.getAllBillingItemsOfContract(c));
        }
        for (BillingItem b: selectedBillingItems) {
            labels.add(b.getStatus().getStateName());
        }
        Set<String> set = new HashSet<>(labels);
        labels.clear();
        labels.addAll(set);
        return labels;
    }

    public List<Integer> getContractCount() {
        List<BillingItem> selectedBillingItems = new ArrayList<>();
        for(Contract c : selectedContracts) {
            selectedBillingItems.addAll(databaseService.getAllBillingItemsOfContract(c));
        }
        List<String> labels = getContractLabels();
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
