package de.contracktor.controller;

import de.contracktor.DatabaseService;
import de.contracktor.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AdminConsoleController {

    @Autowired
    UserManager userManager;

    @Autowired
    DatabaseService databaseService;

    // Function:
    // - links to all admin settings pages
    @GetMapping("/admin")
    public String getAdminConsole(Model model) {
        model.addAttribute("userManager", userManager);
        return "adminConsole";
    }

    @PostMapping("/admin")
    public String getURL(@RequestParam String url, Model model) {
        try {

            databaseService.setURL(url);
            System.out.println(databaseService.getURL());

        } catch (Exception e) {

        }

        model.addAttribute("userManager", userManager);
        return "adminConsole";
    }
}
