package de.contracktor.controller;

import de.contracktor.DatabaseService;
import de.contracktor.model.BillingItem;
import de.contracktor.model.BillingUnit;
import de.contracktor.repository.BillingItemRepository;
import de.contracktor.repository.BillingUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ItemController {

    @Autowired
    BillingItemRepository billingItemRepository;

    @Autowired
    DatabaseService databaseService;

    @GetMapping("/billingitems")
    public String getBillingItems(Model model){
        model.addAttribute("items",billingItemRepository.findAll());
        return "billingitem-list";
    }

    @PostMapping("/billingitems")
    public String getBillingItems(@RequestParam String search, Model model){
        model.addAttribute("search",search);
        model.addAttribute("items",billingItemRepository.findByBillingItemIDContains(search));
        return "billingitem-list";
    }

    @GetMapping("/billingitems/{itemId}/details")
    public String getBillingItemDetails(@PathVariable int itemId,Model model){
        BillingItem item = databaseService.getBillingItemByID(itemId);
        List<BillingItem> subitems = item.getBillingItems();

        model.addAttribute("item", item);
        model.addAttribute("subitems", subitems);
        return "billingitem-details";
    }
}
