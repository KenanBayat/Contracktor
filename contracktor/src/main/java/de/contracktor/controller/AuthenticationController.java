package de.contracktor.controller;

import de.contracktor.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationController {

    @GetMapping("/register")
    public String registrationController(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "registerTest";
    }

}
