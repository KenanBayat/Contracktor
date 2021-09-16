package de.contracktor.controller;

import de.contracktor.DatabaseService;
import de.contracktor.UserManager;
import de.contracktor.model.*;
import de.contracktor.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BillingItemController {

    @Autowired
    BillingItemRepository billingItemRepository;

    @Autowired
    StateTransitionRepository stateTransitionRepository;

    @Autowired
    StateRepository stateRepository;
    
    @Autowired
    DatabaseService databaseService;

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    UserManager userManager;

    @Autowired
    BillingUnitRepository billingUnitRepository;

    @Autowired
    OrganisationRepository organisationRepository;

    List<BillingItem> searchedBillingItems = new ArrayList<>();

    BillingItem billingItem = null;

    @GetMapping("/billingitems")
    public String getBillingItems(Model model) {
        model.addAttribute("userManager", userManager);
        model.addAttribute("billingitems", databaseService.getAllBillingItems());
        model.addAttribute("filter", "");
        return "billingitems";
    }

    @GetMapping("/billingitem")
    public String getBillingItem(Model model) {
        List<StateTransition> transitions = getStateTransition();
        boolean noChanges = true;
        for(StateTransition transition : transitions) {
            if (billingItem.getStatus().getId() == transition.getStartState().getId()) {
                noChanges = false;
            }
        }
        model.addAttribute("noChanges", noChanges);
        model.addAttribute("userManager", userManager);
        model.addAttribute("billingitem", billingItem);
        model.addAttribute("filter", "");
        model.addAttribute("transitions", getStateTransition());
        model.addAttribute("states", stateRepository.findAll());
        model.addAttribute("comment", "");
        return "billingitem";
    }

    @PostMapping("/billingitem")
    public String getBillingItem(@RequestParam String id, Model model) {
        billingItem = billingItemRepository.findByBillingItemID(id).get();
        List<StateTransition> transitions = getStateTransition();
        boolean noChanges = true;
        for(StateTransition transition : transitions) {
            if (billingItem.getStatus().getId() == transition.getStartState().getId()) {
                noChanges = false;
            }
        }
        model.addAttribute("noChanges", noChanges);
        model.addAttribute("userManager", userManager);
        model.addAttribute("billingitem", billingItem);
        model.addAttribute("filter", "");
        model.addAttribute("transitions", getStateTransition());
        model.addAttribute("states", stateRepository.findAll());
        model.addAttribute("comment", "");


        return "billingitem";
    }

    @PostMapping("/billingitem/add")
    public String addBillingItem (@RequestParam String billingItemID, @RequestParam String billingUnitID, @RequestParam String unit, @RequestParam double quantity,
                                  @RequestParam double pricePerUnit, @RequestParam double totalPrice, @RequestParam String ifc, @RequestParam String state, @RequestParam String shortDescription, Model model) {

        if (!userManager.hasCurrentUserWritePerm()) {
            throw new AuthorizationServiceException("No access");
        }

        billingItem = billingItemRepository.findByBillingItemID(billingItem.getBillingItemID()).get();
        BillingItem item = new BillingItem(billingItemID, billingUnitID, unit, quantity, pricePerUnit, totalPrice, ifc, stateRepository.findByStateName(state), shortDescription, new ArrayList<>());
        item = billingItemRepository.save(item);
        List<BillingItem> list = billingItem.getBillingItems();
        list.add(item);
        billingItem.setBillingItems(list);
        billingItem = billingItemRepository.save(billingItem);



        String id = billingItem.getBillingItemID();
        billingItem = billingItemRepository.findByBillingItemID(id).get();

        List<StateTransition> transitions = getStateTransition();
        boolean noChanges = true;
        for(StateTransition transition : transitions) {
            if (billingItem.getStatus().getId() == transition.getStartState().getId()) {
                noChanges = false;
            }
        }

        model.addAttribute("noChanges", noChanges);
        model.addAttribute("comment", "");
        model.addAttribute("userManager", userManager);
        model.addAttribute("billingitem", billingItem);
        model.addAttribute("filter", "");
        model.addAttribute("transitions", getStateTransition());
        model.addAttribute("states", stateRepository.findAll());
        return "billingitem";
    }

        @GetMapping("/billingitem/billingitems")
    public String getBillingItemsOfBillingitem(Model model) {
        List<BillingItem> items = billingItem.getBillingItems();
            model.addAttribute("userManager", userManager);
        model.addAttribute("billingitems", items);
        model.addAttribute("filter", "");
        return "billingitemsOfBillingitem";
    }

    @GetMapping("/reports")
    public String getReports(Model model) {
        List<Report> reports = reportRepository.findAll().stream().filter(report -> billingItem.getBillingItemID().equals(report.getBillingItem().getBillingItemID())).collect(Collectors.toList());

        model.addAttribute("userManager", userManager);
        model.addAttribute("reports", reports);
        model.addAttribute("filter", "");
        return "reports";
    }

    @PostMapping("/billingitem/state")
    public String getBillingItem(@RequestParam int id, @RequestParam String comment, Model model) {

        int reportId;
        if(reportRepository.count() == 0) {
            reportId = 1;
        } else {
            reportId = reportRepository.findFirstByOrderByReportIDDesc().getReportID() + 1;
        }
        Report report = new Report(reportId, billingItem, organisationRepository.findByOrganisationName(userManager.getCurrentOrganisation()), Instant.now().toEpochMilli(), userManager.getCurrentUserName(), comment);
        report = reportRepository.save(report);

        billingItem.setStatus(stateRepository.findById(id).get());
        billingItem = billingItemRepository.save(billingItem);
        List<StateTransition> transitions = getStateTransition();
        boolean noChanges = true;
        for(StateTransition transition : transitions) {
            if (billingItem.getStatus().getId() == transition.getStartState().getId()) {
                noChanges = false;
            }
        }

        model.addAttribute("comment", "");
        model.addAttribute("userManager", userManager);
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

        model.addAttribute("userManager", userManager);
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

        model.addAttribute("userManager", userManager);
        model.addAttribute("billingitems", searchedBillingItems);
        model.addAttribute("filter", "");
        return "billingitems";
    }

    public List<StateTransition> getStateTransition() {
        List<StateTransition> transitions = stateTransitionRepository.findAll();
        Contract contract = databaseService.getContractOfBillingItem(billingItem);
        if(contract.getContractor().equals(userManager.getCurrentOrganisation())) {
            transitions = transitions.stream().filter(transition -> transition.getContractor()).collect(Collectors.toList());
        } else {
            transitions = transitions.stream().filter(transition -> !transition.getContractor()).collect(Collectors.toList());
        }
        return  transitions;
    }
}
