package de.contracktor.controller;

import de.contracktor.controller.dato.EditUserDato;
import de.contracktor.controller.dato.ManageUserDato;
import de.contracktor.controller.dato.RegisterUserDato;
import de.contracktor.model.Organisation;
import de.contracktor.model.UserAccount;
import de.contracktor.model.Role;

import de.contracktor.UserManager;
import de.contracktor.model.Address;
import de.contracktor.model.Organisation;
import de.contracktor.repository.AddressRepository;
import de.contracktor.repository.OrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PageController {


    @Autowired
    UserManager userManager;

    @Autowired
    OrganisationRepository organisationRepository;
    //TODO Remove

    @GetMapping("/")
    public String getLandingPage() {
        return "landing";
    }

    @GetMapping("/statistic")
    public String generateStatistic() {
        return "statistic";
    }

    @GetMapping("/projects")
    public String getProjectList() {
        return "project-list";
    }

    @GetMapping("/project-details")
    public String getProjectDetails(@RequestParam(value = "projectId") String projectId, Model model) {
        model.addAttribute("projectId", projectId);
        return "project-details";
    }

    @GetMapping("/contracts")
    public String getContractList() {
        return "contract-list";
    }

    @GetMapping("/contract-details")
    public String getContractDetails(@RequestParam(value = "contractId") String contractId, Model model) {
        model.addAttribute("contractId", contractId);
        return "contract-details";
    }

    @GetMapping("/billingitems")
    public String getBillingItems() {
        return "billingitem-list";
    }

    @GetMapping("/billingitem-details")
    public String getBillingitemDetails(@RequestParam(value = "billingitemId") String billingitemId, Model model) {
        model.addAttribute("billingitemId", billingitemId);
        return "billingitem-details";
    }

    @GetMapping("/admin/register")
    public String getRegisterAdminPage(Model model) {
        List<Organisation> organisations = List.of(
                new Organisation("Hochtief"),
                new Organisation("Züblin")
        );
        model.addAttribute("organisations", organisations);
        model.addAttribute("user", new RegisterUserDato());
        return "register-sysadmin";
    }

    @PostMapping("/admin/register")
    public String setAdmin(@ModelAttribute RegisterUserDato user, Model model) {
        if(user.getIsSysadmin() == null) {
            user.setIsSysadmin(false);
        }
        if(user.getIsAdmin() == null) {
            user.setIsAdmin(false);
        }


        System.out.println(user.getUsername() + ", " + user.getForename() + ", " + user.getSurname() + ", " + user.getOrganisation() + ", " + user.getPassword()+ ", " + user.getPasswordCheck()+ ", " + user.getIsAdmin() + ", " + user.getIsSysadmin());

        List<Organisation> organisations = List.of(
                new Organisation("Hochtief"),
                new Organisation("Züblin")
        );
        model.addAttribute("organisations", organisations);
        model.addAttribute("user", new RegisterUserDato());
        return "register-sysadmin";
    }

    @GetMapping("/admin/manage-user")
    public String getUserList(Model model) {
        Organisation hochtief = new Organisation("Hochtief");
        Organisation zublin = new Organisation("Züblin");
        List<Organisation> organisations = List.of(
                zublin, hochtief
        );
        List<UserAccount> users = List.of(
                new UserAccount("lion", "pwd", "Lion", "Grabau", hochtief, true, true, new ArrayList<Role>()),
                new UserAccount("alex", "hallo", "Alex", "Meier", hochtief, true, false, new ArrayList<Role>()),
                new UserAccount("nils", "imDreieck", "Nils", "Fischer", zublin, false, false, new ArrayList<Role>())

        );
        ManageUserDato userDato = new ManageUserDato(organisations, users);
        model.addAttribute("userlist", userDato);
        return "user-list";
    }

    @PostMapping("/admin/manage-user")
    public String getFilteredUserList(@RequestParam String search, Model model) {
        model.addAttribute("search", search);
        Organisation hochtief = new Organisation("Hochtief");
        Organisation zublin = new Organisation("Züblin");
        List<Organisation> organisations = List.of(
                zublin, hochtief
        );
        List<UserAccount> users = List.of(
                new UserAccount("lion", "pwd", "Lion", "Grabau", hochtief, true, true, new ArrayList<Role>()),
                new UserAccount("alex", "hallo", "Alex", "Meier", hochtief, true, false, new ArrayList<Role>()),
                new UserAccount("nils", "imDreieck", "Nils", "Fischer", zublin, false, false, new ArrayList<Role>())

        );
        ManageUserDato userDato = new ManageUserDato(organisations, users);
        userDato.setUserList(userDato.getFilteredUserList(search));
        model.addAttribute("userlist", userDato);
        model.addAttribute("editUser", new EditUserDato());
        return "user-list";
    }


    @GetMapping("/admin/manage-user/delete/{userId}")
    public String deleteUser(@PathVariable String userId, Model model) {
        System.out.println(userId);
        Organisation hochtief = new Organisation("Hochtief");
        Organisation zublin = new Organisation("Züblin");
        List<Organisation> organisations = List.of(
                zublin, hochtief
        );
        List<UserAccount> users = List.of(
                new UserAccount("lion", "pwd", "Lion", "Grabau", hochtief, true, true, new ArrayList<Role>()),
                new UserAccount("alex", "hallo", "Alex", "Meier", hochtief, true, false, new ArrayList<Role>()),
                new UserAccount("nils", "imDreieck", "Nils", "Fischer", zublin, false, false, new ArrayList<Role>())

        );
        ManageUserDato userDato = new ManageUserDato(organisations, users);
        model.addAttribute("userlist", userDato);
        model.addAttribute("editUser", new EditUserDato());
        return "user-list";
    }

    @GetMapping("/admin/manage-user/edit/{userId}")
    public String editUser(@PathVariable String userId, Model model) {
        Organisation hochtief = new Organisation("Hochtief");
        Organisation zublin = new Organisation("Züblin");
        List<UserAccount> users = List.of(
                new UserAccount("lion", "pwd", "Lion", "Grabau", hochtief, true, true, new ArrayList<Role>()),
                new UserAccount("alex", "hallo", "Alex", "Meier", hochtief, true, false, new ArrayList<Role>()),
                new UserAccount("nils", "imDreieck", "Nils", "Fischer", zublin, false, false, new ArrayList<Role>())

        );
        for(UserAccount user : users) {
            if(user.getUsername().equals(userId)) {
                model.addAttribute("oldUser", user);
            }
        }
        model.addAttribute("newUser", new EditUserDato());
        return "edit-user";
    }

    @PostMapping("/admin/manage-user/edit")
    public String setUserEdit(@ModelAttribute EditUserDato newUser, Model model) {
        System.out.println(newUser.getNewUsername());
        Organisation hochtief = new Organisation("Hochtief");
        Organisation zublin = new Organisation("Züblin");
        List<Organisation> organisations = List.of(
                zublin, hochtief
        );
        List<UserAccount> users = List.of(
                new UserAccount("lion", "pwd", "Lion", "Grabau", hochtief, true, true, new ArrayList<Role>()),
                new UserAccount("alex", "hallo", "Alex", "Meier", hochtief, true, false, new ArrayList<Role>()),
                new UserAccount("nils", "imDreieck", "Nils", "Fischer", zublin, false, false, new ArrayList<Role>())

        );
        ManageUserDato userDato = new ManageUserDato(organisations, users);
        model.addAttribute("userlist", userDato);

        UserAccount oldUser;
        for(UserAccount user : users) {
            if(user.getUsername().equals(newUser.getOldUsername())) {
                oldUser = user;
            }
        }

        return "user-list";
    }
}
