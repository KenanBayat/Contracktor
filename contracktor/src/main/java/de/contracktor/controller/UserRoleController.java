package de.contracktor.controller;

import de.contracktor.UserManager;
import de.contracktor.model.Organisation;
import de.contracktor.model.Role;
import de.contracktor.model.UserAccount;
import de.contracktor.repository.OrganisationRepository;
import de.contracktor.repository.RoleRepository;
import de.contracktor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserRoleController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrganisationRepository organisationRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserManager userManager;

    @GetMapping("/admin/userrole")
    public String getUserRolePage(Model model) {
        // Data:
        List<UserAccount> users = userRepository.findAll();
        List<Organisation> organisations = organisationRepository.findAll();
        List<Role> roles = roleRepository.findAll();

        if(!userManager.isCurrentUserAppAdmin()) {
            organisations = List.of(organisationRepository.findByOrganisationName(userManager.getCurrentOrganisation()));
        }

        // Logic:


        // Model:
        model.addAttribute("users", users);
        model.addAttribute("organisations", organisations);
        model.addAttribute("roles", roles);
        return "userRole";
    }

    @PostMapping("/admin/userrole/add")
    public String getAddUserRolePage(@RequestParam int addRole, @RequestParam int userId, Model model) {
        // Data:
        List<Organisation> organisations = organisationRepository.findAll();
        List<Role> roles = roleRepository.findAll();


        if(!userManager.isCurrentUserAppAdmin()) {
            organisations = List.of(organisationRepository.findByOrganisationName(userManager.getCurrentOrganisation()));
        }

        // Logic:
        Role role = roleRepository.findById(addRole).get();
        UserAccount user = userRepository.findById(userId).get();
        List<Role> userRole = user.getRoles();
        userRole.add(role);
        user.setRoles(userRole);
        user = userRepository.save(user);
        List<UserAccount> users = userRepository.findAll();


        // Model:
        model.addAttribute("users", users);
        model.addAttribute("organisations", organisations);
        model.addAttribute("roles", roles);
        return "userRole";
    }

    @PostMapping("/admin/userrole/delete")
    public String getDeleteUserRolePage(@RequestParam int addRole, @RequestParam int userId, Model model) {
        // Data:
        List<Organisation> organisations = organisationRepository.findAll();
        List<Role> roles = roleRepository.findAll();

        if(!userManager.isCurrentUserAppAdmin()) {
            organisations = List.of(organisationRepository.findByOrganisationName(userManager.getCurrentOrganisation()));
        }

        // Logic:
        Role role = roleRepository.findById(addRole).get();
        UserAccount user = userRepository.findById(userId).get();
        List<Role> userRole = user.getRoles();
        List<Role> newRoles = new ArrayList<>();
        for (Role r : userRole) {
            if(role != r) {
                newRoles.add(r);
            }
        }
        user.setRoles(newRoles);
        user = userRepository.save(user);
        List<UserAccount> users = userRepository.findAll();


        // Model:
        model.addAttribute("users", users);
        model.addAttribute("organisations", organisations);
        model.addAttribute("roles", roles);
        return "userRole";
    }

    @PostMapping("/admin/userrole/search")
    public String getFilteredOrganisationManagement(@RequestParam String search, Model model) {
        // Data:
        List<UserAccount> users = userRepository.findAll();
        List<Organisation> organisations = organisationRepository.findAll();
        List<Role> roles = roleRepository.findAll();

        if(!userManager.isCurrentUserAppAdmin()) {
            organisations = List.of(organisationRepository.findByOrganisationName(userManager.getCurrentOrganisation()));
        }

        // Logic:
        users = users.stream()
                .filter(user -> (user.getUsername().toLowerCase().contains(search.toLowerCase()) | user.getSurname().toLowerCase().contains(search.toLowerCase()) | user.getForename().toLowerCase().contains(search.toLowerCase()) | user.getOrganisation().getOrganisationName().toLowerCase().contains(search.toLowerCase())))
                .collect(Collectors.toList());

        // Model attributes:
        model.addAttribute("users", users);
        model.addAttribute("organisations", organisations);
        model.addAttribute("roles", roles);

        return "userRole";
    }
}
