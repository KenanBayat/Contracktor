package de.sopro.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

	// Value is overridden via injection from file application.properties
	@Value("${home.welcome}")
	String message = "dummy";

	// Returns HTML page created from populated model and template home.html
	@GetMapping("/")
	public String showHome(Model model) {
		model.addAttribute("message", message);
		return "home";
	}

	// Example for request providing a parameter via the URL path
	@GetMapping("/hello/{name}")
	public String showHello(@PathVariable String name, Model model) {
		model.addAttribute("message", "Hello, " + name + "! Nice to meet you!");
		return "home";
	}

}
