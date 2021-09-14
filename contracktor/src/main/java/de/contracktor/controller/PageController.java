package de.contracktor.controller;

import de.contracktor.UserManager;
import de.contracktor.model.Role;
import de.contracktor.security.ContracktorUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@Controller
public class PageController {


    @GetMapping("/")
    public String getLandingPage() {
        return "landing";
    }

}
