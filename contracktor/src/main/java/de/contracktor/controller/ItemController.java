package de.contracktor.controller;

import de.contracktor.repository.BillingItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ItemController {

    @Autowired
    BillingItemRepository billingItemRepository;

    @GetMapping("/billingitems")
    public String getBillingItems(Model model){
        model.addAttribute("items",billingItemRepository.findAll());
        return "billingitem-list";
    }

    @GetMapping("/billingItems/{itemId}/details")
    public String getBillingItemDetails(@PathVariable int itemId,Model model){
        model.addAttribute("item", billingItemRepository.findById(itemId));
        return "billingitem-details";
    }
}
