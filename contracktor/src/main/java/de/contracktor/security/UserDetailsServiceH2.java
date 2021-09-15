package de.contracktor.security;

import de.contracktor.model.UserAccount;
import de.contracktor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceH2 implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public ContracktorUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserAccount> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("The requested account does not exist.");
        }

        return new ContracktorUserDetails(user.get());
    }

}
