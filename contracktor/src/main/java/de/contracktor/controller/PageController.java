package de.contracktor.controller;

import de.contracktor.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PageController {

    @Autowired
    UserManager userManager;

    @GetMapping("/")
    public String getLandingPage(Model model) {
        model.addAttribute("userManager", userManager);
        return "landing";
    }
}
