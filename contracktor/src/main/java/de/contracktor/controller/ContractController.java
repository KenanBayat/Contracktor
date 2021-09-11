package de.contracktor.controller;

import de.contracktor.model.BillingItem;
import de.contracktor.model.BillingUnit;
import de.contracktor.repository.BillingUnitRepository;
import de.contracktor.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    @GetMapping("/contract/{contractId}/details")
    public String getContractDetails(@PathVariable int contractId, Model model){

        model.addAttribute("contract", contractRepository.findById(contractId));

        List<BillingUnit> units = (List<BillingUnit>) billingUnitRepository.findAll();
        List<BillingItem> items = new ArrayList<>();
        for(BillingUnit unit: units){
            List<BillingItem> bItems = unit.getBillingItems();
            for(BillingItem bItem:bItems){
                items.add(bItem);
            }
        }

        model.addAttribute("items",items);
        return "contract-details";
    }
}
