package de.contracktor.controller;

import de.contracktor.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContractStatisticController {

    @Autowired
    ContractRepository contractRepository;

    @GetMapping("/contract-statistic")
    public String getContractStatistic(Model model) {
        return "contract-statistic";
    }
}
