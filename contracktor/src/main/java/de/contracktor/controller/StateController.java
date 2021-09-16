package de.contracktor.controller;

import de.contracktor.UserManager;
import de.contracktor.model.State;
import de.contracktor.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class StateController {

    @Autowired
    StateRepository stateRepository;

    @Autowired
    UserManager userManager;

    List<State> searchedStates = new ArrayList<State>();

    @GetMapping("/admin/state")
    public String getStatePage(Model model) {
        // Data:
        List<State> states = stateRepository.findAll();

        // Logic:


        // Model:
        model.addAttribute("userManager", userManager);
        model.addAttribute("states", states);
        model.addAttribute("filter", "");

        return "state";
    }

    //----------------------------------------------------------------------------
    // Search Organisation

    @PostMapping("/admin/state/search")
    public String getFilteredStatesManagement(@RequestParam String search, Model model) {
        // Data:
        List<State> states = stateRepository.findAll();

        // Logic:
        System.out.println(search);
        states = states.stream().filter(state -> state.getStateName().toLowerCase().contains(search.toLowerCase()))
                .collect(Collectors.toList());
        searchedStates = states;

        // Model attributes:
        model.addAttribute("userManager", userManager);
        model.addAttribute("states", states);
        model.addAttribute("filter", "");

        return "state";
    }

    //----------------------------------------------------------------------------
    // Delete State

    @PostMapping("/admin/state/delete")
    public String deleteOrganisation(@RequestParam int id, Model model) {
        // Data:

        // Logic:
        stateRepository.deleteById(id);
        List<State> states = stateRepository.findAll();

        // Model attributes:
        model.addAttribute("userManager", userManager);
        model.addAttribute("states", states);
        model.addAttribute("filter", "");

        return "state";
    }

    //----------------------------------------------------------------------------
    // Add new State

    @PostMapping("/admin/state/add")
    public String addOrganisation(@RequestParam String newState, Model model) {
        // Data:

        // Logic:
        State state = new State(newState);
        state = stateRepository.save(state);
        List<State> states = stateRepository.findAll();

        // Model attributes:
        model.addAttribute("userManager", userManager);
        model.addAttribute("states", states);
        model.addAttribute("filter", "");

        return "state";
    }

    //---------------------------------------------------------------------------
    // Filter table


    // Function of the Page:
    // - Add new organisations
    // - Search in organisations
    // - List all organisations
    // - Delete organisations
    @GetMapping("/admin/state/{filter}")
    public String getOrganisationManagement(@PathVariable String filter, Model model) {
        // Data:
        List<State> states = searchedStates;

        // Logic:
        if(searchedStates.isEmpty()) {
            states = stateRepository.findAll();
        }
        if(filter.equals("id_asc")) {
            states = states.stream().sorted(Comparator.comparing(State::getId)).collect(Collectors.toList());
        }
        if(filter.equals("id_desc")) {
            states = states.stream().sorted(Comparator.comparing(State::getId)).collect(Collectors.toList());
            Collections.reverse(states);
        }
        if(filter.equals("name_asc")) {
            states = states.stream().sorted(Comparator.comparing(State::getStateName)).collect(Collectors.toList());
        }
        if(filter.equals("name_desc")) {
            states = states.stream().sorted(Comparator.comparing(State::getStateName)).collect(Collectors.toList());
            Collections.reverse(states);
        }

        // Model attributes:
        model.addAttribute("userManager", userManager);
        model.addAttribute("states", states);
        model.addAttribute("filter", filter);

        return "state";
    }
}
