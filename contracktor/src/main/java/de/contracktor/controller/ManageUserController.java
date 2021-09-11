package de.contracktor.controller;

import de.contracktor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ManageUserController {

    @Autowired
    UserRepository userRepository;
}
