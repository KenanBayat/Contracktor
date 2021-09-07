package de.contracktor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String getProjectPage(Model model) {
        model.addAttribute("project","1");
        model.addAttribute("name","Projekt1");
        return "project-details";
    }
}
