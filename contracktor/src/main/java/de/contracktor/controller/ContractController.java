package de.contracktor.controller;

import de.contracktor.controller.dato.BillingUnitDato;
import de.contracktor.controller.dato.ContractDato;
import de.contracktor.model.BillingItem;
import de.contracktor.model.BillingUnit;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ContractController {

    @GetMapping("/contracts")
    public String getContractList(Model model){
        ContractDato contracts = ContractDato.getInstance();
        model.addAttribute("contracts",contracts);
        return "contract-list";
    }

    @GetMapping("/contract/{contractId}/details")
    public String getContractDetails(@PathVariable int contractId, Model model){
        ContractDato contracts = ContractDato.getInstance();

        BillingUnitDato billingUnits = BillingUnitDato.getInstance();
        List<BillingUnit> units = billingUnits.getBunits();

        model.addAttribute("contract", contracts.findById(contractId));

        List<BillingItem> items = new ArrayList<>();
        for(BillingUnit unit: units){
            items.addAll(unit.getBillingItems());
        }
        model.addAttribute("items",items);
        return "contract-details";
    }
}
