package de.contracktor.controller;

import de.contracktor.model.User;
import de.contracktor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationController {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/register")
    public String registrationController(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        user.setPassword(encoder.encode("Test"));
        user.setUsername("jonas");
        user.setForename("Jonas");
        user.setIsAdmin(false);
        user.setIsApplicationAdmin(false);
        user.setSurname("Testo");
        userRepository.save(user);
        return "registerTest";
    }

}
