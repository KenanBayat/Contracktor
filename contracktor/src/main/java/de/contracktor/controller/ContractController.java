package de.contracktor.controller;

import de.contracktor.DatabaseService;
import de.contracktor.model.BillingItem;
import de.contracktor.model.BillingUnit;
import de.contracktor.model.Contract;
import de.contracktor.repository.BillingUnitRepository;
import de.contracktor.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ContractController {

    @Autowired
    ContractRepository contractRepository;

    @Autowired
    BillingUnitRepository billingUnitRepository;

    @GetMapping("/contracts")
    public String getContractList(Model model){
        model.addAttribute("contracts",contractRepository.findAll());
        return "contract-list";
    }

    @PostMapping("/contracts")
    public String getSearchedContractList(@RequestParam String search, Model model){
        model.addAttribute("search",search);
        model.addAttribute("contracts",contractRepository.findByNameContains(search));
        return "contract-list";
    }

    @GetMapping("/contract/{contractId}/details")
    public String getContractDetails(@PathVariable int contractId, Model model){
        Contract contract = contractRepository.findById(contractId).get();
        model.addAttribute("contract", contract);

        List<BillingItem> items = DatabaseService.getAllBillingItemsOfContract(contract);
        model.addAttribute("items",items);
        return "contract-details";
    }
}
