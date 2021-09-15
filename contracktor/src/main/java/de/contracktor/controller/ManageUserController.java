package de.contracktor.controller;

import de.contracktor.UserManager;
import de.contracktor.model.Organisation;
import de.contracktor.model.UserAccount;
import de.contracktor.repository.OrganisationRepository;
import de.contracktor.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class ManageUserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserManager userManager;

    @Autowired
    OrganisationRepository organisationRepository;

    @GetMapping("/admin/user")
    public String getUserManagementPage(Model model) {
        // Data:
        List<UserAccount> users = (List<UserAccount>) userRepository.findAll();
        List<Organisation> organisations = organisationRepository.findAll();

        // Logic:
        // sort organisation list
        organisations = organisations.stream().sorted(Comparator.comparing(Organisation::getOrganisationName)).collect(Collectors.toList());

        model.addAttribute("users", users);
        model.addAttribute("organisations", organisations);

        return "manageUsers";
    }

    @GetMapping("/admin/user/edit")
    public String getEditUserManagementPage(Model model) {
        // Data:
        List<UserAccount> users = (List<UserAccount>) userRepository.findAll();
        List<Organisation> organisations = organisationRepository.findAll();

        // Logic:
        // sort organisation list
        organisations = organisations.stream().sorted(Comparator.comparing(Organisation::getOrganisationName)).collect(Collectors.toList());

        model.addAttribute("users", users);
        model.addAttribute("organisations", organisations);

        return "manageUsers";
    }

    @PostMapping("/admin/user/edit")
    public String getEditUserManagementPage(
            @RequestParam int id,
            @RequestParam String username,
            @RequestParam String forename,
            @RequestParam String surname,
            @RequestParam String organisation,
            @RequestParam(value = "admin", required = false) String admin,
            Model model) {

        // Data:
        List<UserAccount> users = (List<UserAccount>) userRepository.findAll();
        List<Organisation> organisations = organisationRepository.findAll();
        boolean isAdmin = false;
        boolean isSysadmin = false;

        // Logic:
        // sort organisation list
        if(admin == null) {
            admin = "no";
        }
        if(admin.contains("admin")) {
            isAdmin = true;
        }
        if(admin.contains("sysadmin")) {
            isSysadmin = true;
        }
        if(userRepository.existsById(id)) {
            Optional<UserAccount> user = userRepository.findById(id);
        }

        UserAccount user = userRepository.findById(id).get();
        user.setUsername(username);
        user.setForename(forename);
        user.setSurname(surname);
        user.setOrganisation(organisationRepository.findByOrganisationName(organisation));
        user.setIsAdmin(isAdmin);
        user.setIsApplicationAdmin(isSysadmin);
        UserAccount saveUser = userRepository.save(user);

        organisations = organisations.stream().sorted(Comparator.comparing(Organisation::getOrganisationName)).collect(Collectors.toList());

        model.addAttribute("users", users);
        model.addAttribute("organisations", organisations);

        return "manageUsers";
    }

    @GetMapping("/admin/user/delete")
    public String getDeleteUserManagementPage(Model model) {
        // Data:
        List<UserAccount> users = (List<UserAccount>) userRepository.findAll();
        List<Organisation> organisations = organisationRepository.findAll();

        // Logic:
        // sort organisation list
        organisations = organisations.stream().sorted(Comparator.comparing(Organisation::getOrganisationName)).collect(Collectors.toList());

        model.addAttribute("users", users);
        model.addAttribute("organisations", organisations);

        return "manageUsers";
    }

    @PostMapping("/admin/user/delete")
    public String getDeleteUserManagementPage(@RequestParam int deleteId, Model model) {
        // Data:
        List<Organisation> organisations = organisationRepository.findAll();

        // Logic:
        // sort organisation list
        userRepository.deleteById(deleteId);
        List<UserAccount> users = (List<UserAccount>) userRepository.findAll();
        organisations = organisations.stream().sorted(Comparator.comparing(Organisation::getOrganisationName)).collect(Collectors.toList());

        model.addAttribute("users", users);
        model.addAttribute("organisations", organisations);

        return "manageUsers";
    }

    @GetMapping("/admin/user/password")
    public String getPasswordUserManagementPage(Model model) {
        // Data:
        List<UserAccount> users = (List<UserAccount>) userRepository.findAll();
        List<Organisation> organisations = organisationRepository.findAll();

        // Logic:
        // sort organisation list
        organisations = organisations.stream().sorted(Comparator.comparing(Organisation::getOrganisationName)).collect(Collectors.toList());

        model.addAttribute("users", users);
        model.addAttribute("organisations", organisations);

        return "manageUsers";
    }

    @PostMapping("/admin/user/password")
    public String getPasswordUserManagementPage(@RequestParam int userId, @RequestParam String password, @RequestParam String passwordCheck, Model model) {
        // Data:
        List<Organisation> organisations = organisationRepository.findAll();
        List<UserAccount> users = (List<UserAccount>) userRepository.findAll();

        // Logic:
        // sort organisation list
        Optional<UserAccount> optional = userRepository.findById(userId);
        UserAccount user = optional.get();
        user.setPassword(password);
        userRepository.deleteById(userId);
        UserAccount add = userManager.addUser(user);
        organisations = organisations.stream().sorted(Comparator.comparing(Organisation::getOrganisationName)).collect(Collectors.toList());

        model.addAttribute("users", users);
        model.addAttribute("organisations", organisations);

        return "manageUsers";
    }

    @GetMapping("/admin/user/search")
    public String getSearchUserManagementPage(Model model) {
        // Data:
        List<UserAccount> users = (List<UserAccount>) userRepository.findAll();
        List<Organisation> organisations = organisationRepository.findAll();

        // Logic:
        // sort organisation list
        organisations = organisations.stream().sorted(Comparator.comparing(Organisation::getOrganisationName)).collect(Collectors.toList());

        model.addAttribute("users", users);
        model.addAttribute("organisations", organisations);

        return "manageUsers";
    }

    @PostMapping("/admin/user/search")
    public String getSearchManagementPage(@RequestParam String search, Model model) {
        // Data:
        List<Organisation> organisations = organisationRepository.findAll();
        List<UserAccount> users = (List<UserAccount>) userRepository.findAll();

        // Logic:
        // sort organisation list
        users = users.stream()
                .filter(user -> (user.getUsername().toLowerCase().contains(search.toLowerCase()) | user.getSurname().toLowerCase().contains(search.toLowerCase()) | user.getForename().toLowerCase().contains(search.toLowerCase()) | user.getOrganisation().getOrganisationName().toLowerCase().contains(search.toLowerCase())))
                .collect(Collectors.toList());

        organisations = organisations.stream().sorted(Comparator.comparing(Organisation::getOrganisationName)).collect(Collectors.toList());

        model.addAttribute("users", users);
        model.addAttribute("organisations", organisations);

        return "manageUsers";
    }

}
