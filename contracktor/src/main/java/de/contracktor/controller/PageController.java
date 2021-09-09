package de.contracktor.controller;

import de.contracktor.UserManager;
import de.contracktor.model.Address;
import de.contracktor.model.Organisation;
import de.contracktor.model.User;
import de.contracktor.repository.AddressRepository;
import de.contracktor.repository.OrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PageController {

    @Autowired
    UserManager userManager;

    @Autowired
    OrganisationRepository organisationRepository;
    //TODO Remove

	@Autowired
	private AddressRepository addressRepo;
	
	Address address1;
	Address address2;
    
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
                new Organisation("Hochtief"),
                new Organisation("Züblin")
        );
        //TODO Remove
        organisationRepository.save(organisations.get(0));
        organisationRepository.save(organisations.get(1));

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
        //TODO Remove
        user.setOrganisation(organisationRepository.findByOrganisationName("Hochtief"));
        userManager.addUser(user);



        System.out.println(user.getUsername() + ", " + user.getForename() + ", " + user.getSurname() + ", " + user.getOrganisation() + ", " + user.getPassword()+ ", " + user.getIsAdmin() + ", " + user.getIsApplicationAdmin());
       
        List<Organisation> organisations = List.of(
                new Organisation("Hochtief"),
                new Organisation("Züblin")
        );
        model.addAttribute("organisations", organisations);
        model.addAttribute("user", new User());
        return "register-sysadmin";
    }
}
