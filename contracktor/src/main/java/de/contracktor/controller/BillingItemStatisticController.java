package de.contracktor.controller;

import de.contracktor.model.BillingItem;
import de.contracktor.model.Contract;
import de.contracktor.repository.BillingItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class BillingItemStatisticController {

    @Autowired
    BillingItemRepository billingItemRepository;

    List<BillingItem> selectedBillingItems = new ArrayList<>();

    List<BillingItem> searchedBillingItems = new ArrayList<>();

    @GetMapping("/billingitem-statistic")
    public String getBillingitemStatistic(Model model) {

        model.addAttribute("labels", getLabels());
        model.addAttribute("count", getCount());
        model.addAttribute("billingitems", billingItemRepository.findAll());
        model.addAttribute("selectedBillingitems", selectedBillingItems);
        model.addAttribute("filter", "");
        return "billingitem-statistic";
    }

    @PostMapping("/billingitem-statistic/addAll")
    public String getAddAllBillingItemStatistic(Model model) {
        selectedBillingItems = billingItemRepository.findAll();

        model.addAttribute("labels", getLabels());
        model.addAttribute("count", getCount());
        model.addAttribute("billingitems", billingItemRepository.findAll());
        model.addAttribute("selectedBillingitems", selectedBillingItems);
        model.addAttribute("filter", "");

        return "billingitem-statistic";
    }

    @PostMapping("/billingitem-statistic/removeAll")
    public String getRemoveAllBillingItemStatistic(Model model) {
        selectedBillingItems = new ArrayList<>();

        model.addAttribute("labels", getLabels());
        model.addAttribute("count", getCount());
        model.addAttribute("billingitems", billingItemRepository.findAll());
        model.addAttribute("selectedBillingitems", selectedBillingItems);
        model.addAttribute("filter", "");

        return "billingitem-statistic";
    }

    @PostMapping("/billingitem-statistic/remove")
    public String getRemoveProjectStatistic(@RequestParam String id, Model model) {
        List<BillingItem> billingItems = billingItemRepository.findAll();
        BillingItem billingItem = billingItemRepository.findByBillingItemID(id).get();
        List<BillingItem> newSelected = new ArrayList<>();
        for (BillingItem b : selectedBillingItems) {
            if(b.getBillingItemID() != billingItem.getBillingItemID()) {
                newSelected.add(b);
            }
        }
        selectedBillingItems = newSelected;

        model.addAttribute("labels", getLabels());
        model.addAttribute("count", getCount());
        model.addAttribute("billingitems", billingItemRepository.findAll());
        model.addAttribute("selectedBillingitems", selectedBillingItems);
        model.addAttribute("filter", "");
        return "billingitem-statistic";
    }

    @PostMapping("/billingitem-statistic/add")
    public String getAddProjectStatistic(@RequestParam String id, Model model) {
        BillingItem billingItem = billingItemRepository.findByBillingItemID(id).get();
        boolean contains = false;
        for (BillingItem b : selectedBillingItems) {
            if(b.getBillingItemID() == billingItem.getBillingItemID()) {
                contains = true;
            }
        }
        if (!contains) {
            selectedBillingItems.add(billingItem);
        }

        model.addAttribute("labels", getLabels());
        model.addAttribute("count", getCount());
        model.addAttribute("billingitems", billingItemRepository.findAll());
        model.addAttribute("selectedBillingitems", selectedBillingItems);
        model.addAttribute("filter", "");
        return "billingitem-statistic";
    }

    @GetMapping("/billingitem-statistic/{filter}")
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

        model.addAttribute("labels", getLabels());
        model.addAttribute("count", getCount());
        model.addAttribute("billingitems", sortList);
        model.addAttribute("selectedBillingitems", selectedBillingItems);
        model.addAttribute("filter", filter);
        return "billingitem-statistic";
    }

    @PostMapping("/billingitem-statistic/search")
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

        model.addAttribute("labels", getLabels());
        model.addAttribute("count", getCount());
        model.addAttribute("billingitems", searchedBillingItems);
        model.addAttribute("selectedBillingitems", selectedBillingItems);
        model.addAttribute("filter", "");
        return "billingitem-statistic";
    }


    //------------------------------------------------------
    // Helper
    public List<String> getLabels() {
        List<String> labels = new ArrayList<>();
        List<BillingItem> items = getSelectedBillingItems();
        for (BillingItem b : items) {
            labels.add(b.getStatus().getStateName());
        }
        Set<String> set = new HashSet<>(labels);
        labels.clear();
        labels.addAll(set);
        return labels;
    }

    public List<Integer> getCount() {
        List<BillingItem> items = getSelectedBillingItems();
        List<String> labels = getLabels();
        List<Integer> count = new ArrayList<>();
        for(String s : labels) {
            int counter = 0;
            for (BillingItem b : selectedBillingItems) {
                if (b.getStatus().getStateName().equals(s)) {
                    counter++;
                }
            }
            count.add(counter);
        }
        return count;
    }

    public List<BillingItem> getSelectedBillingItems() {
        List<BillingItem> billingItems = selectedBillingItems;
        List<BillingItem> items = new ArrayList<>();
        for (BillingItem b : billingItems) {
            items.addAll(b.getBillingItems());
        }
        items.addAll(billingItems);
        return items;
    }
}
