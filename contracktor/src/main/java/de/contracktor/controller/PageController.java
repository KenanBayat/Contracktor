package de.contracktor.controller;

import de.contracktor.UserManager;
import de.contracktor.model.Role;
import de.contracktor.security.ContracktorUserDetails;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


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
