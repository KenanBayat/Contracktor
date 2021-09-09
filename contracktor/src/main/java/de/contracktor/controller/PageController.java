package de.contracktor.controller;

import de.contracktor.controller.dato.ManageUserDato;
import de.contracktor.controller.dato.RegisterUserDato;
import de.contracktor.model.Organisation;
import de.contracktor.model.User;
import de.contracktor.model.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PageController {

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

    @GetMapping("/admin")
    public String getAdminConsole() {
        return "admin-console";
    }

    @GetMapping("/admin/register")
    public String getRegisterAdminPage(Model model) {
        List<Organisation> organisations = List.of(
                new Organisation("Hochtief", "Elbstraße", "7", "Hamburg", "22406", "Deutschland"),
                new Organisation("Züblin", "Haupstraße", "26", "Hamburg", "22317", "Deutschland")
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
                new Organisation("Hochtief", "Elbstraße", "7", "Hamburg", "22406", "Deutschland"),
                new Organisation("Züblin", "Haupstraße", "26", "Hamburg", "22317", "Deutschland")
        );
        model.addAttribute("organisations", organisations);
        model.addAttribute("user", new RegisterUserDato());
        return "register-sysadmin";
    }

    @GetMapping("/admin/manage-user")
    public String getUserList(Model model) {
        Organisation hochtief = new Organisation("Hochtief", "Elbstraße", "7", "Hamburg", "22406", "Deutschland");
        Organisation zublin = new Organisation("Züblin", "Haupstraße", "26", "Hamburg", "22317", "Deutschland");
        List<Organisation> organisations = List.of(
                zublin, hochtief
        );
        List<User> users = List.of(
                new User("lion", "pwd", "Lion", "Grabau", hochtief, true, true, new ArrayList<Role>()),
                new User("alex", "hallo", "Alex", "Meier", hochtief, true, false, new ArrayList<Role>()),
                new User("nils", "imDreieck", "Nils", "Fischer", zublin, false, false, new ArrayList<Role>())

        );
        ManageUserDato userDato = new ManageUserDato(organisations, users);
        model.addAttribute("userlist", userDato);
        return "user-list";
    }

    @PostMapping("/admin/manage-user")
    public String getFilteredUserList(@RequestParam String search, Model model) {
        model.addAttribute("search", search);
        Organisation hochtief = new Organisation("Hochtief", "Elbstraße", "7", "Hamburg", "22406", "Deutschland");
        Organisation zublin = new Organisation("Züblin", "Haupstraße", "26", "Hamburg", "22317", "Deutschland");
        List<Organisation> organisations = List.of(
                zublin, hochtief
        );
        List<User> users = List.of(
                new User("lion", "pwd", "Lion", "Grabau", hochtief, true, true, new ArrayList<Role>()),
                new User("alex", "hallo", "Alex", "Meier", hochtief, true, false, new ArrayList<Role>()),
                new User("nils", "imDreieck", "Nils", "Fischer", zublin, false, false, new ArrayList<Role>())

        );
        ManageUserDato userDato = new ManageUserDato(organisations, users);
        userDato.setUserList(userDato.getFilteredUserList(search));
        model.addAttribute("userlist", userDato);
        return "user-list";
    }
}
