package de.contracktor.controller;

import de.contracktor.model.*;
import de.contracktor.repository.ContractRepository;
import de.contracktor.repository.ProjectRepository;
import de.contracktor.repository.UserRepository;
import de.contracktor.security.ContracktorUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
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

    @GetMapping("/api/download")
    @ResponseBody
    public APIResponse downloadController(Model model, Principal principal) {
        String username = principal.getName();
        return apiDownloadConstructor(username);
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
        response.setBillingUnits();

    }

    private boolean hasWritePerm(User user) {
        List<Permission> permissions = user.getRoles().stream().map((p) -> p.getPermission()).collect(Collectors.toList());
        return permissions.contains(new Permission("w"));
    }
}
