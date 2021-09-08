package de.contracktor.controller;

import de.contracktor.model.Organisation;
import de.contracktor.model.User;
import de.contracktor.repository.OrganisationRepository;
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

    @Autowired
    private OrganisationRepository organisationRepository;

    @GetMapping("/register")
    public String registrationController(Model model) {
        Organisation testorg = new Organisation("testOrg","test","test","test","test","test");
        User user = new User("test",encoder.encode("test"),"test","test",testorg,false,false,null);
        organisationRepository.save(testorg);
        userRepository.save(user);

        return "landing";
    }

}
