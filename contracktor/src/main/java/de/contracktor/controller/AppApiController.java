package de.contracktor.controller;

import de.contracktor.model.*;
import de.contracktor.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class AppApiController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private BillingUnitRepository billingUnitRepository;

    @Autowired
    private BillingItemRepository billingItemRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private StateTransitionRepository stateTransitionRepository;

    @Autowired
    private ReportRepository reportRepository;

    @GetMapping("/api/download")
    @ResponseBody
    public APIResponse downloadController(Model model) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        if (user == null) {
            return new APIResponse("NOT_AUTHENTICATED");
        }
        return apiDownloadConstructor(user);
    }

    @PostMapping("/api/update")
    @ResponseBody
    public APIResponse updateController(@RequestParam(name = "json") APIUpdate update) {
        for (BillingItem billingItem : update.getBillingItemList()) {
            Optional<BillingItem> savedItem = billingItemRepository.findByBillingItemID(billingItem.getBillingItemID());
            if (savedItem.isPresent() && savedItem.get().getLastModified() >= billingItem.getLastModified()) {
                billingItemRepository.save(billingItem);
            }
        }

        for (Report report : update.getReportList()) {
            reportRepository.save(report);
        }

        return new APIResponse("OK");
    }

    @RequestMapping("/api/login")
    @ResponseBody
    public void loginController() {
    }

    private APIResponse apiDownloadConstructor(String username) {
        Optional<UserAccount> user =  userRepository.findByUsername(username);
        if (user.isEmpty()) {
            return new APIResponse("UNKNOWN_USER");
        } else if (!hasPerm(user.get(), new Permission("r"))) {
            return new APIResponse("NO_READ_PERM");
        }

        APIResponse response = new APIResponse();
        Organisation organisation = user.get().getOrganisation();
        response.setWritePerm(hasPerm(user.get(), new Permission("w")));
        response.setProjects(projectRepository.findByOwner_OrganisationNameIgnoreCase(organisation.getOrganisationName()));
        response.setContracts(contractRepository.findByContractorIgnoreCaseOrConsigneeIgnoreCase(organisation.getOrganisationName(),
                organisation.getOrganisationName()));
        response.setBillingUnits(billingUnitRepository.findByContractIsIn(response.getContracts()));
        response.setStates((List<State>) stateRepository.findAll());
        response.setStateTransitions((List<StateTransition>) stateTransitionRepository.findAll());
        response.setReports(reportRepository.findByOrganisation(organisation));
        response.setStatus("OK");
        return response;
    }

    private boolean hasPerm(UserAccount user, Permission permission) {
        List<Permission> permissions = user.getRoles().stream().map((p) -> p.getPermission()).collect(Collectors.toList());
        return permissions.contains(new Permission("w"));
    }
}
