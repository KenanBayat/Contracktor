package de.contracktor;

import de.contracktor.model.UserAccount;
import de.contracktor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserManager {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    public UserAccount addUser(UserAccount user) throws AuthorizationServiceException {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new AuthorizationServiceException("Username already exists");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);

    }

    public void removeUser(UserAccount user) {
        if(!userRepository.existsByUsername(user.getUsername())) {
            throw new AuthorizationServiceException("User does not exist");
        }
        userRepository.delete(user);
    }

    public UserAccount updateUser(UserAccount user) {
        if (!userRepository.existsByUsername(user.getUsername())) {
            throw new AuthorizationServiceException("User does not exist");
        }

        return userRepository.save(user);
    }


}
