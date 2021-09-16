package de.contracktor.controller;

import de.contracktor.DatabaseService;
import de.contracktor.model.BillingItem;
import de.contracktor.model.BillingUnit;
import de.contracktor.model.StateTransition;
import de.contracktor.repository.BillingItemRepository;
import de.contracktor.repository.BillingUnitRepository;
import de.contracktor.repository.StateRepository;
import de.contracktor.repository.StateTransitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ItemController {

    @Autowired
    BillingItemRepository billingItemRepository;

    @Autowired
    StateTransitionRepository stateTransitionRepository;

    @Autowired
    StateRepository stateRepository;
    
    @Autowired
    DatabaseService databaseService;

    List<BillingItem> searchedBillingItems = new ArrayList<>();

    BillingItem billingItem = null;

    @GetMapping("/billingitems")
    public String getBillingItems(Model model) {
        model.addAttribute("billingitems", databaseService.getAllBillingItems());
        model.addAttribute("filter", "");
        return "billingitems";
    }

    @GetMapping("/billingitem")
    public String getBillingItem(Model model) {
        model.addAttribute("billingitem", billingItem);
        model.addAttribute("filter", "");
        model.addAttribute("transitions", stateTransitionRepository.findAll());
        model.addAttribute("states", stateRepository.findAll());
        return "billingitem";
    }

    @PostMapping("/billingitem")
    public String getBillingItem(@RequestParam String id, Model model) {
        billingItem = billingItemRepository.findByBillingItemID(id).get();
        model.addAttribute("billingitem", billingItem);
        model.addAttribute("filter", "");
        model.addAttribute("transitions", stateTransitionRepository.findAll());
        model.addAttribute("states", stateRepository.findAll());
        return "billingitem";
    }

    @PostMapping("/billingitem/add")
    public String addBillingItem (@RequestParam String billingItemID, @RequestParam String billingUnitID, @RequestParam String unit, @RequestParam double quantity,
                                  @RequestParam double pricePerUnit, @RequestParam double totalPrice, @RequestParam String ifc, @RequestParam String state, @RequestParam String shortDescription, Model model) {


        billingItem = billingItemRepository.findByBillingItemID(billingItem.getBillingItemID()).get();
        BillingItem item = new BillingItem(billingItemID, "", unit, quantity, pricePerUnit, totalPrice, ifc, stateRepository.findByStateName(state), shortDescription, new ArrayList<>());
        item = billingItemRepository.save(item);
        List<BillingItem> list = billingItem.getBillingItems();
        list.add(item);
        billingItem.setBillingItems(list);
        billingItem = billingItemRepository.save(billingItem);



        String id = billingItem.getBillingItemID();
        billingItem = billingItemRepository.findByBillingItemID(id).get();

        model.addAttribute("billingitem", billingItem);
        model.addAttribute("filter", "");
        model.addAttribute("transitions", stateTransitionRepository.findAll());
        model.addAttribute("states", stateRepository.findAll());
        return "billingitem";
    }

        @GetMapping("/billingitem/billingitems")
    public String getBillingItemsOfBillingitem(Model model) {
        List<BillingItem> items = billingItem.getBillingItems();
        model.addAttribute("billingitems", items);
        model.addAttribute("filter", "");
        return "billingitemsOfBillingitem";
    }

    @PostMapping("/billingitem/state")
    public String getBillingItem(@RequestParam int id, Model model) {
        billingItem.setStatus(stateRepository.findById(id).get());
        billingItem = billingItemRepository.save(billingItem);
        List<StateTransition> transitions = stateTransitionRepository.findAll();
        boolean noChanges = true;
        for(StateTransition transition : transitions) {
            if (billingItem.getStatus().getId() == transition.getStartState().getId()) {
                noChanges = false;
            }
        }

        model.addAttribute("noChanges", noChanges);
        model.addAttribute("billingitem", billingItem);
        model.addAttribute("filter", "");
        model.addAttribute("transitions", transitions);
        return "billingitem";
    }

    @GetMapping("/billingitems/{filter}")
    public String getFilteredBillingItemStatistic(@PathVariable String filter, Model model) {
        List<BillingItem> sortList = searchedBillingItems;

        if(searchedBillingItems.isEmpty()) {
            sortList = billingItemRepository.findAll();
        }
        if(filter.equals("description_asc")) {
            sortList = sortList.stream().sorted(Comparator.comparing(BillingItem::getLowerDescription)).collect(Collectors.toList());
        }
        if(filter.equals("description_desc")) {
            sortList = sortList.stream().sorted(Comparator.comparing(BillingItem::getLowerDescription)).collect(Collectors.toList());
            Collections.reverse(sortList);
        }
        if(filter.equals("id_asc")) {
            sortList = sortList.stream().sorted(Comparator.comparing(BillingItem::getLowerID)).collect(Collectors.toList());
        }
        if(filter.equals("id_desc")) {
            sortList = sortList.stream().sorted(Comparator.comparing(BillingItem::getLowerID)).collect(Collectors.toList());
            Collections.reverse(sortList);
        }
        if(filter.equals("quantity_asc")) {
            sortList = sortList.stream().sorted(Comparator.comparing(BillingItem::getQuantity)).collect(Collectors.toList());
        }
        if(filter.equals("quantity_desc")) {
            sortList = sortList.stream().sorted(Comparator.comparing(BillingItem::getQuantity)).collect(Collectors.toList());
            Collections.reverse(sortList);
        }
        if(filter.equals("price_asc")) {
            sortList = sortList.stream().sorted(Comparator.comparing(BillingItem::getPricePerUnit)).collect(Collectors.toList());
        }
        if(filter.equals("price_desc")) {
            sortList = sortList.stream().sorted(Comparator.comparing(BillingItem::getPricePerUnit)).collect(Collectors.toList());
            Collections.reverse(sortList);
        }
        if(filter.equals("total_asc")) {
            sortList = sortList.stream().sorted(Comparator.comparing(BillingItem::getTotalPrice)).collect(Collectors.toList());
        }
        if(filter.equals("total_desc")) {
            sortList = sortList.stream().sorted(Comparator.comparing(BillingItem::getTotalPrice)).collect(Collectors.toList());
            Collections.reverse(sortList);
        }
        if(filter.equals("status_asc")) {
            sortList = sortList.stream().sorted(Comparator.comparing(BillingItem::getLowerStatus)).collect(Collectors.toList());
        }
        if(filter.equals("status_desc")) {
            sortList = sortList.stream().sorted(Comparator.comparing(BillingItem::getLowerStatus)).collect(Collectors.toList());
            Collections.reverse(sortList);
        }

        model.addAttribute("billingitems", sortList);
        model.addAttribute("filter", filter);
        return "billingitems";
    }

    @PostMapping("/billingitems/search")
    public String getSearchAllBillingItemStatistic(@RequestParam String search, Model model) {
        List<BillingItem> billingItems = billingItemRepository.findAll();

        String finalSearch = search.toLowerCase();
        searchedBillingItems = billingItems.stream().filter(item -> item.getShortDescription().toLowerCase().contains(finalSearch)
                                | item.getBillingItemID().toLowerCase().contains(finalSearch)
                                | item.getStatus().getStateName().toLowerCase().contains(finalSearch)
                        // | Double.toString(item.getQuantity()).contains(finalSearch)
                        //     | Double.toString(item.getTotalPrice()).contains(finalSearch)
                        //    | Double.toString(item.getPricePerUnit()).contains(finalSearch)
                )
                .collect(Collectors.toList());

        model.addAttribute("billingitems", searchedBillingItems);
        model.addAttribute("filter", "");
        return "billingitems";
    }
}
