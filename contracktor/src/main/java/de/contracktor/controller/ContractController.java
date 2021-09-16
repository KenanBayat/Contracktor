package de.contracktor.controller;

import de.contracktor.DatabaseService;
import de.contracktor.model.BillingItem;
import de.contracktor.model.BillingUnit;
import de.contracktor.model.Contract;
import de.contracktor.model.State;
import de.contracktor.repository.BillingItemRepository;
import de.contracktor.repository.BillingUnitRepository;
import de.contracktor.repository.ContractRepository;
import de.contracktor.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class ContractController {

    @Autowired
    ContractRepository contractRepository;

    @Autowired
    BillingUnitRepository billingUnitRepository;

    @Autowired
    BillingItemRepository billingItemRepository;

    @Autowired
    StateRepository stateRepository;

    @Autowired
    DatabaseService databaseService;

    List<Contract> searchedContracts = new ArrayList<>();

    Contract contract = null;
    
    @GetMapping("/contracts")
    public String getContracts(Model model) {
        model.addAttribute("contracts", databaseService.getAllContracts());
        model.addAttribute("filter", "");

        return "contracts";
    }

    @GetMapping("/contract")
    public String getContract(Model model) {
        model.addAttribute("contract", contract);
        model.addAttribute("billingUnitIDs", getBillingUnitIDs());
        model.addAttribute("states", stateRepository.findAll());
        return "contract";
    }

    @PostMapping("/contract")
    public String getContract(@RequestParam int id, Model model) {
        contract = contractRepository.findByContractID(id);
        model.addAttribute("contract", contract);
        model.addAttribute("billingUnitIDs", getBillingUnitIDs());
        model.addAttribute("states", stateRepository.findAll());
        return "contract";
    }

    @PostMapping("/contract/add")
    public String addBillingItem(@RequestParam String billingItemID, @RequestParam String billingUnitID, @RequestParam String unit, @RequestParam double quantity,
                                 @RequestParam double pricePerUnit, @RequestParam double totalPrice, @RequestParam String ifc, @RequestParam String state, @RequestParam String shortDescription, Model model) {
        BillingUnit billingUnit;
        List<BillingUnit> billingUnits = billingUnitRepository.findAllByContract(contract);
        if (billingUnitID.equals("Neue anlegen")) {
            billingUnit = new BillingUnit(("BUID_" + billingItemID), "", 0, 0.0, 0.0, contract, new ArrayList<>(), false, "", "", stateRepository.findByStateName("NO_STATUS"));
            System.out.println(billingUnit.getBillingUnitID());
            billingUnit = billingUnitRepository.save(billingUnit);
        } else {
            billingUnit = billingUnitRepository.findByBillingUnitID(billingUnitID);
        }
        BillingItem billingItem = new BillingItem(billingItemID, ("BUID_" + billingItemID), unit, quantity, pricePerUnit, totalPrice, ifc, stateRepository.findByStateName(state), shortDescription, new ArrayList<>());
        billingItem = billingItemRepository.save(billingItem);

        int id = contract.getContractID();
        contract = contractRepository.findByContractID(id);

            billingUnit = billingUnitRepository.findByBillingUnitID(billingUnitID);
            List<BillingItem> current = billingUnit.getBillingItems();
            current.add(billingItem);
            billingUnit.setBillingItems(current);
            billingUnit = billingUnitRepository.save(billingUnit);

        model.addAttribute("contract", contract);
        model.addAttribute("billingUnitIDs", getBillingUnitIDs());
        model.addAttribute("states", stateRepository.findAll());
        return "contract";
    }

    @GetMapping("contract/billingitems")
    public String getItemsOfContract(Model model) {
        List<BillingUnit> billingUnits = new ArrayList<>();
        billingUnits.addAll(billingUnitRepository.findAllByContract(contract));
        List<BillingItem> selectedBillingItems = new ArrayList<>();
        for (BillingUnit bu : billingUnits) {
            selectedBillingItems.addAll(bu.getBillingItems());
        }
        model.addAttribute("billingitems", selectedBillingItems);
        return "itemsOfContract";
    }

    @PostMapping("/contracts/search")
    public String getSearchAllProjectStatistic(@RequestParam String search, Model model) {
        List<Contract> contracts = contractRepository.findAll();

        String finalSearch = search.toLowerCase();
        searchedContracts = contracts.stream().filter(contract -> contract.getName().toLowerCase().contains(finalSearch)
                        | contract.getProject().getName().toLowerCase().contains(finalSearch)
                        | contract.getConsignee().toLowerCase().contains(finalSearch)
                        | contract.getContractor().toLowerCase().contains(finalSearch)
                        | contract.getStatus().getStateName().toLowerCase().contains(finalSearch)
                )
                .collect(Collectors.toList());

        model.addAttribute("contracts", searchedContracts);
        model.addAttribute("filter", "");
        return "contracts";
    }

    @GetMapping("/contracts/{filter}")
    public String getFilteredContracts(@PathVariable String filter, Model model) {
        List<Contract> sortList = searchedContracts;

        if(searchedContracts.isEmpty()) {
            sortList = contractRepository.findAll();
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

        model.addAttribute("contracts", sortList);
        model.addAttribute("filter", filter);

        return "contracts";
    }

    public List<String> getBillingUnitIDs() {
        List<BillingUnit> billingUnits = billingUnitRepository.findAllByContract(contract);
        List<String> ids = new ArrayList<>();
        for (BillingUnit b : billingUnits) {
            ids.add(b.getBillingUnitID());
        }
        ids.add("Neue anlegen");
        return ids;
    }
}
