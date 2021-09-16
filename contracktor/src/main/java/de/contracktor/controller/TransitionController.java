package de.contracktor.controller;

import de.contracktor.UserManager;
import de.contracktor.model.Organisation;
import de.contracktor.model.State;
import de.contracktor.model.StateTransition;
import de.contracktor.repository.StateRepository;
import de.contracktor.repository.StateTransitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TransitionController {

    @Autowired
    StateRepository stateRepository;

    @Autowired
    StateTransitionRepository transitionRepository;

    @Autowired
    UserManager userManager;

    List<StateTransition> searchedTransitions = new ArrayList<>();

    @GetMapping("/admin/transition")
    public String getTransitionPage(Model model) {
        List<State> states = stateRepository.findAll();
        List<StateTransition> transitions = transitionRepository.findAll();

        model.addAttribute("userManager", userManager);
        model.addAttribute("states", states);
        model.addAttribute("transitions", transitions);
        model.addAttribute("filter", "");
        return "transition";
    }

    //----------------------------------------------------------------------------
    // Search

    @PostMapping("/admin/transition/search")
    public String getFilteredTransitionManagement(@RequestParam String search, Model model) {
        // Data:
        List<StateTransition> transitions = transitionRepository.findAll();
        List<State> states = stateRepository.findAll();

        // Logic:
        transitions = transitions.stream()
                .filter(transition -> transition.getStartStateName().toLowerCase().contains(search.toLowerCase()) | transition.getEndStateName().toLowerCase().contains(search.toLowerCase()))
                .collect(Collectors.toList());
        searchedTransitions = transitions;

        // Model attributes:
        model.addAttribute("userManager", userManager);
        model.addAttribute("states", states);
        model.addAttribute("transitions", transitions);
        model.addAttribute("filter", "");

        return "transition";
    }

    //----------------------------------------------------------------------------
    // Delete

    @PostMapping("/admin/transition/delete")
    public String deleteTransition(@RequestParam int id, Model model) {
        // Data:
        List<State> states = stateRepository.findAll();

        // Logic:
        transitionRepository.deleteById(id);
        List<StateTransition> transitions = transitionRepository.findAll();

        // Model attributes:
        model.addAttribute("userManager", userManager);
        model.addAttribute("states", states);
        model.addAttribute("transitions", transitions);
        model.addAttribute("filter", "");

        return "transition";
    }

    //----------------------------------------------------------------------------
    // Add

    @PostMapping("/admin/transition/add")
    public String addTransition(@RequestParam int start, @RequestParam int end, @RequestParam String who, Model model) {
        // Data:
        State startS = stateRepository.findById(start).get();
        State endS = stateRepository.findById(end).get();
        boolean nehmer = false;
        boolean geber = false;

        // Logic:
        if (who.equals("geber")) {
            geber = true;
        }
        if (who.equals("nehmer")) {
            nehmer = true;
        }
        StateTransition transition = new StateTransition(startS, endS, geber, nehmer);
        transition = transitionRepository.save(transition);
        List<StateTransition> transitions = transitionRepository.findAll();
        List<State> states = stateRepository.findAll();

        // Model attributes:
        model.addAttribute("userManager", userManager);
        model.addAttribute("states", states);
        model.addAttribute("transitions", transitions);
        model.addAttribute("filter", "");

        return "transition";
    }

    //---------------------------------------------------------------------------
    // Filter table

    @GetMapping("/admin/transition/{filter}")
    public String getTransitionManagement(@PathVariable String filter, Model model) {
        // Data:
        List<StateTransition> transitions = searchedTransitions;
        List<State> states = stateRepository.findAll();

        // Logic:
        if(searchedTransitions.isEmpty()) {
            transitions = transitionRepository.findAll();
        }
        if(filter.equals("start_asc")) {
            transitions = transitions.stream().sorted(Comparator.comparing(StateTransition::getStartStateName)).collect(Collectors.toList());
        }
        if(filter.equals("start_desc")) {
            transitions = transitions.stream().sorted(Comparator.comparing(StateTransition::getStartStateName)).collect(Collectors.toList());
            Collections.reverse(transitions);
        }
        if(filter.equals("end_asc")) {
            transitions = transitions.stream().sorted(Comparator.comparing(StateTransition::getEndStateName)).collect(Collectors.toList());
        }
        if(filter.equals("end_desc")) {
            transitions = transitions.stream().sorted(Comparator.comparing(StateTransition::getEndStateName)).collect(Collectors.toList());
            Collections.reverse(transitions);
        }

        // Model attributes:
        model.addAttribute("userManager", userManager);
        model.addAttribute("states", states);
        model.addAttribute("transitions", transitions);
        model.addAttribute("filter", filter);

        return "transition";
    }

}
