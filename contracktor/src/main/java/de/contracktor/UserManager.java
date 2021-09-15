package de.contracktor;

import de.contracktor.model.Role;
import de.contracktor.model.UserAccount;
import de.contracktor.repository.UserRepository;
import de.contracktor.security.ContracktorUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.sasl.AuthenticationException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserManager {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    public UserAccount addUser(UserAccount user) {
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

    public String getCurrentUserName() {
        return getPrincipal().getUsername();
    }

    public String getCurrentOrganisation() {
        return getPrincipal().getOrganisationName();
    }

    public int getCurrentOrganisationId() {
        return getPrincipal().getOrganisationId();
    }

    public boolean isCurrentUserAdmin() {
        return getPrincipal().isAdmin();
    }

    public boolean isCurrentUserAppAdmin() {
        return getPrincipal().isAppAdmin();
    }

    public List<Role> getCurrentUserRoles() {
        return getPrincipal().getRoles();
    }

    public boolean hasCurrentUserWritePerm() {
        return getCurrentUserRoles().stream().map((r) -> r.getPermission().getPermissionName()).collect(Collectors.toList()).contains("w")
                || isCurrentUserAdmin() || isCurrentUserAppAdmin();
    }

    public boolean hasCurrentUserReadPerm() {
        return getCurrentUserRoles().stream().map((r) -> r.getPermission().getPermissionName()).collect(Collectors.toList()).contains("r")
               || hasCurrentUserWritePerm();
    }

    private ContracktorUserDetails getPrincipal() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            return (ContracktorUserDetails) auth.getPrincipal();
    }

}
