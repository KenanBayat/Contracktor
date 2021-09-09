package de.contracktor.controller;

import de.contracktor.model.Organisation;
import de.contracktor.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        model.addAttribute("user", new User());
        return "register-sysadmin";
    }

    @PostMapping("/admin/register")
    public String setAdmin(@ModelAttribute User user, Model model) {
        if(user.getIsApplicationAdmin() == null) {
            user.setIsApplicationAdmin(false);
        }
        if(user.getIsAdmin() == null) {
            user.setIsAdmin(false);
        }


        System.out.println(user.getUsername() + ", " + user.getForename() + ", " + user.getSurname() + ", " + user.getOrganisation() + ", " + user.getPassword()+ ", " + user.getIsAdmin() + ", " + user.getIsApplicationAdmin());

        List<Organisation> organisations = List.of(
                new Organisation("Hochtief", "Elbstraße", "7", "Hamburg", "22406", "Deutschland"),
                new Organisation("Züblin", "Haupstraße", "26", "Hamburg", "22317", "Deutschland")
        );
        model.addAttribute("organisations", organisations);
        model.addAttribute("user", new User());
        return "register-sysadmin";
    }
}
