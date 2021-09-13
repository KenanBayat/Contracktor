package de.contracktor.controller;

import de.contracktor.model.*;
import de.contracktor.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;
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

    @Autowired
    private PictureRepository pictureRepository;

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
        List<BillingItemUpdate> billingItemUpdates = update.getBillingItemUpdates();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<UserAccount> userAccount = userRepository.findByUsername(username);
        if (userAccount.isEmpty()) {
            return new APIResponse("UNKNOWN_USER");
        } else if (!hasPerm(userAccount.get(),"w")) {
            return new APIResponse("NO_WRITE_PERM");
        }

        for (BillingItemUpdate billingItemUpdate : billingItemUpdates) {
            Optional<BillingItem> savedItem = billingItemRepository.findByBillingItemID(billingItemUpdate.getBillingItemID());
            if (savedItem.isEmpty()) {
                return new APIResponse("UNKNOWN_BILLINGITEM");
            }

            if (savedItem.get().getLastModified() >= billingItemUpdate.getLastModified()) {
                savedItem.get().setStatus(billingItemUpdate.getNewState());
                savedItem.get().setLastModified(billingItemUpdate.getLastModified());
                billingItemRepository.save(savedItem.get());
            }
        }

        for (Report report : update.getReportList()) {
            for (Picture picture : report.getPictures()) {
                pictureRepository.save(picture);
            }
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
        } else if (!hasPerm(user.get(), "r") && !hasPerm(user.get(),"w")) {
            return new APIResponse("NO_READ_PERM");
        }

        APIResponse response = new APIResponse();
        Organisation organisation = user.get().getOrganisation();
        response.setWritePerm(hasPerm(user.get(), "w"));
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

    private boolean hasPerm(UserAccount user, String permission) {
        List<String> permissions = user.getRoles().stream().map((r) -> r.getPermission().getPermissionName()).collect(Collectors.toList());
        return permissions.contains(permission);
    }
}
