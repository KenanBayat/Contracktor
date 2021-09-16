package de.contracktor.controller;

import de.contracktor.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StatisticController {

    @Autowired
    UserManager userManager;

    @GetMapping("/statistic")
    public String getStatisticSelection(Model model) {

        model.addAttribute("userManager", userManager);
        return "statistic";
    }

    @PostMapping("/statistic")
    public String getSelection(@RequestParam String choice) {
        if(choice.equals("1")) {
            return "redirect:/project-statistic";
        }
        if(choice.equals("2")) {
            return "redirect:/contract-statistic";
        }
        if(choice.equals("3")) {
            return "redirect:/billingitem-statistic";
        }
        return "statistic";
    }
}
