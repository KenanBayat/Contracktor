package de.contracktor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminConsoleController {

    // Function:
    // - links to all admin settings pages
    @GetMapping("/admin")
    public String getAdminConsole() {
        return "adminConsole";
    }
}
