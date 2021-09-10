package de.contracktor.controller;

import de.contracktor.model.Organisation;
import de.contracktor.repository.OrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class OrganisationController {

    // Connecting to the database
    @Autowired
    OrganisationRepository organisationRepository;

    List<Organisation> searchedOrganisations = new ArrayList<Organisation>();


    // Function of the Page:
    // - Add new organisations
    // - Search in organisations
    // - List all organisations
    // - Delete organisations
    @GetMapping("/admin/organisation")
    public String getOrganisationManagement(Model model) {
        // Data:
        List<Organisation> organisationList = organisationRepository.findAll();

        // Logic:

        // Model attributes:
        model.addAttribute("organisations", organisationList);
        model.addAttribute("filter", "");

        return "organisation";
    }

    //----------------------------------------------------------------------------
    // Search Organisation

    // Function of the Page:
    // - Add new organisations
    // - Search in organisations
    // - List all organisations
    // - Delete organisations
    @GetMapping("/admin/organisation/search/")
    public String getSearchOrganisationManagement(Model model) {
        // Data:
        List<Organisation> organisationList = organisationRepository.findAll();

        // Logic:

        // Model attributes:
        model.addAttribute("organisations", organisationList);
        model.addAttribute("filter", "");

        return "organisation";
    }

    // Function of the page:
    // - Add new organisations
    // - Search in organisations
    // - List filtered organisations by search string
    // - Delete organisations
    @PostMapping("/admin/organisation/search")
    public String getFilteredOrganisationManagement(@RequestParam String search, Model model) {
        // Data:
        List<Organisation> organisationList = organisationRepository.findAll();

        // Logic:
        organisationList = organisationList.stream()
                .filter(organisation -> organisation.getOrganisationName().toLowerCase().contains(search.toLowerCase()))
                .collect(Collectors.toList());
        searchedOrganisations = organisationList;

        // Model attributes:
        model.addAttribute("organisations", organisationList);
        model.addAttribute("filter", "");

        return "organisation";
    }

    //----------------------------------------------------------------------------
    // Delete Organisation

    // Function of the Page:
    // - Add new organisations
    // - Search in organisations
    // - List all organisations
    // - Delete organisations
    @GetMapping("/admin/organisation/delete")
    public String getDeleteOrganisationManagement(Model model) {
        // Data:
        List<Organisation> organisationList = organisationRepository.findAll();

        // Logic:

        // Model attributes:
        model.addAttribute("organisations", organisationList);
        model.addAttribute("filter", "");

        return "organisation";
    }

    // Function of the page:
    // - Add new organisations
    // - Search in organisations
    // - List organisations
    // - Delete organisations
    @PostMapping("/admin/organisation/delete")
    public String deleteOrganisation(@RequestParam int id, Model model) {
        // Data:

        // Logic:
        System.out.println(id);
        organisationRepository.deleteById(id);
        List<Organisation> organisationList = organisationRepository.findAll();

        // Model attributes:
        model.addAttribute("organisations", organisationList);
        model.addAttribute("filter", "");

        return "organisation";
    }

    //----------------------------------------------------------------------------
    // Add new Organisation

    // Function of the Page:
    // - Add new organisations
    // - Search in organisations
    // - List all organisations
    // - Delete organisations
    @GetMapping("/admin/organisation/add")
    public String getAddOrganisationManagement(Model model) {
        // Data:
        List<Organisation> organisationList = organisationRepository.findAll();

        // Logic:

        // Model attributes:
        model.addAttribute("organisations", organisationList);
        model.addAttribute("filter", "");

        return "organisation";
    }

    // Function of the page:
    // - Add new organisations
    // - Search in organisations
    // - List organisations
    // - Delete organisations
    @PostMapping("/admin/organisation/add")
    public String addOrganisation(@RequestParam String newOrganisation, Model model) {
        // Data:

        // Logic:
        Organisation organisation = new Organisation(newOrganisation);
        organisation = organisationRepository.save(organisation);
        List<Organisation> organisationList = organisationRepository.findAll();

        // Model attributes:
        model.addAttribute("organisations", organisationList);
        model.addAttribute("filter", "");

        return "organisation";
    }

    //---------------------------------------------------------------------------
    // Filter table


    // Function of the Page:
    // - Add new organisations
    // - Search in organisations
    // - List all organisations
    // - Delete organisations
    @GetMapping("/admin/organisation/{filter}")
    public String getOrganisationManagement(@PathVariable String filter, Model model) {
        // Data:
        List<Organisation> organisationList = searchedOrganisations;

        // Logic:
        if(searchedOrganisations.isEmpty()) {
            organisationList = organisationRepository.findAll();
        }
        if(filter.equals("id_asc")) {
            organisationList = organisationList.stream().sorted(Comparator.comparing(Organisation::getId)).collect(Collectors.toList());
        }
        if(filter.equals("id_desc")) {
            organisationList = organisationList.stream().sorted(Comparator.comparing(Organisation::getId)).collect(Collectors.toList());
            Collections.reverse(organisationList);
        }
        if(filter.equals("name_asc")) {
            organisationList = organisationList.stream().sorted(Comparator.comparing(Organisation::getOrganisationName)).collect(Collectors.toList());
        }
        if(filter.equals("name_desc")) {
            organisationList = organisationList.stream().sorted(Comparator.comparing(Organisation::getOrganisationName)).collect(Collectors.toList());
            Collections.reverse(organisationList);
        }

        // Model attributes:
        model.addAttribute("organisations", organisationList);
        model.addAttribute("filter", filter);

        return "organisation";
    }
}
