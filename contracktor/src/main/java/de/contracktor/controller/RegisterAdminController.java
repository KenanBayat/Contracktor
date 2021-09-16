package de.contracktor.controller;

import de.contracktor.UserManager;
import de.contracktor.model.Organisation;
import de.contracktor.model.Role;
import de.contracktor.model.UserAccount;
import de.contracktor.repository.OrganisationRepository;
import de.contracktor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RegisterAdminController {

    @Autowired
    UserManager userManager;

    @Autowired
    OrganisationRepository organisationRepository;

    // Function:
    // - Form to register new user
    @GetMapping("/admin/register")
    public String getRegisterAdminPage(Model model) {
        // Data:
        List<Organisation> organisations = organisationRepository.findAll();
        boolean isSystemAdmin = userManager.isCurrentUserAppAdmin();
        String orgName = userManager.getCurrentOrganisation();
        System.out.println(userManager.isCurrentUserAppAdmin());

        // Model:
        model.addAttribute("organisations", organisations);

        // For form input
        model.addAttribute("userManager", userManager);
        model.addAttribute("userOrganisation", orgName);
        model.addAttribute("isSystemAdmin", isSystemAdmin);
        model.addAttribute("username", "");
        model.addAttribute("forename", "");
        model.addAttribute("surname", "");
        model.addAttribute("organisation", "");
        model.addAttribute("password", "");
        model.addAttribute("passwordCheck", "");
        model.addAttribute("admin", "");

        return "registerAdmin";
    }

    @PostMapping("/admin/register")
    public String setAdmin(@RequestParam String username,
                           @RequestParam String forename,
                           @RequestParam String surname,
                           @RequestParam String organisation,
                           @RequestParam String password,
                           @RequestParam(value = "admin", required = false) String admin,
                           Model model) {

        // Data:
        boolean isAdmin = false;
        boolean isSysadmin = false;
        List<Organisation> organisations = organisationRepository.findAll();
        Organisation org = organisationRepository.findByOrganisationName(organisation);
        boolean isSystemAdmin = userManager.isCurrentUserAppAdmin();
        String orgName = userManager.getCurrentOrganisation();

        // Logic:
        if(admin == null) {
            admin = "no";
        }
        if(admin.contains("admin")) {
            isAdmin = true;
        }
        if(admin.contains("sysadmin")) {
            isSysadmin = true;
        }

        System.out.println(admin);

        UserAccount user = userManager.addUser(new UserAccount(username, password, forename, surname, org,
                isAdmin, isSysadmin, new ArrayList<Role>()));

        // Model:
        model.addAttribute("organisations", organisations);

        // For form input
        model.addAttribute("userManager", userManager);
        model.addAttribute("userOrganisation", orgName);
        model.addAttribute("isSystemAdmin", isSystemAdmin);
        model.addAttribute("username", "");
        model.addAttribute("forename", "");
        model.addAttribute("surname", "");
        model.addAttribute("organisation", "");
        model.addAttribute("password", "");
        model.addAttribute("passwordCheck", "");
        model.addAttribute("admin", "");

        return "registerAdmin";
    }
}
