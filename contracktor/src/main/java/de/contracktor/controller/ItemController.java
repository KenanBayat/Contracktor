package de.contracktor.controller;

import de.contracktor.controller.dato.BillingItemsDato;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ItemController {

    @GetMapping("/billingItems")
    public String getBillingItems(Model model){
        BillingItemsDato items = BillingItemsDato.getInstance();
        model.addAttribute("items",items.getBillingItems());
        return "billingitem-list";
    }

    @GetMapping("/billingItems/{itemId}/details")
    public String getBillingItemDetails(@PathVariable String itemId,Model model){
        BillingItemsDato items = BillingItemsDato.getInstance();
        model.addAttribute("item", items.findById(itemId));
        return "billingitem-details";
    }
}
