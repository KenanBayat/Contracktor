package de.contracktor.controller;

import de.contracktor.model.*;
import de.contracktor.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class WebAPI {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private BillingUnitRepository billingUnitRepository;

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

    private APIResponse apiDownloadConstructor(String username) {
        Optional<User> user =  userRepository.findByUsername(username);
        if (user.isEmpty()) {
            return new APIResponse("UNKNOWN_USER");
        }
        APIResponse response = new APIResponse();
        Organisation organisation = user.get().getOrganisation();
        response.setWritePerm(hasWritePerm(user.get()));
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

    private boolean hasWritePerm(User user) {
        List<Permission> permissions = user.getRoles().stream().map((p) -> p.getPermission()).collect(Collectors.toList());
        return permissions.contains(new Permission("w"));
    }
}
