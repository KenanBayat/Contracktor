package de.contracktor.controller;

import de.contracktor.repository.BillingItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BillingItemStatisticController {

    @Autowired
    BillingItemRepository billingItemRepository;

    @GetMapping("/billingitem-statistic")
    public String getBillingitemStatistic(Model model) {
        return "billingitem-statistic";
    }
}
