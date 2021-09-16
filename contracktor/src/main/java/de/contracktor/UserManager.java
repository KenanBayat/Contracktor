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
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for managing the user data of the application. Includes methods for performing common user-related database calls
 * and for retrieving information about the currently logged-in user.
 */
@Service
public class UserManager {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    /**
     * Adds the passed user to the database, checking whether a user with the same username already exists.
     * Passwords are encoded before being saved.
     * @param user The user that should be added.
     * @return the user account that was added.
     */
    public UserAccount addUser(UserAccount user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new AuthorizationServiceException("Username already exists");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);

    }

    /**
     * Removes a user from the database, checking first whether a user by that username actually exists.
     * @param user The user that should be deleted.
     */
    public void removeUser(UserAccount user) {
        if(!userRepository.existsByUsername(user.getUsername())) {
            throw new AuthorizationServiceException("User does not exist");
        }
        userRepository.delete(user);
    }

    /**
     * Updates the saved information of a user, checking first whether a user by that username actually exists.
     * @param user The user that should be updated.
     * @return the user account that was updated.
     */
    public UserAccount updateUser(UserAccount user) {
        if (!userRepository.existsByUsername(user.getUsername())) {
            throw new AuthorizationServiceException("User does not exist");
        }

        return userRepository.save(user);
    }

    /**
     * Returns the username of the currently logged-in user.
     * @return the username of the currently logged-in user
     */
    public String getCurrentUserName() {
        return getPrincipal().getUsername();
    }

    /**
     * Returns the name of the organisation that the currently logged-in user belongs to.
     * @return the name of the organisation that the currently logged-in user belongs to.
     */
    public String getCurrentOrganisation() {
        return getPrincipal().getOrganisationName();
    }

    /**
     * Returns the id of the organisation that the currently logged-in user belongs to.
     * @return the id of the organisation that the currently logged-in user belongs to.
     */
    public int getCurrentOrganisationId() {
        return getPrincipal().getOrganisationId();
    }

    /**
     * Returns whether the currently logged-in user is an admin.
     * @return whether the currently logged-in user is an admin.
     */
    public boolean isCurrentUserAdmin() {
        return getPrincipal().isAdmin();
    }

    /**
     * Returns whether the currently logged-in user is an app-admin.
     * @return whether the currently logged-in user is an app-admin.
     */
    public boolean isCurrentUserAppAdmin() {
        return getPrincipal().isAppAdmin();
    }

    /**
     * Returns all roles of the currently logged-in user.
     * @return all roles of the currently logged-in user.
     */
    public List<Role> getCurrentUserRoles() {
        return getPrincipal().getRoles();
    }

    /**
     * Returns whether the currently logged-in user has write-permissions.
     * @return whether the currently logged-in user has write-permissions.
     */
    public boolean hasCurrentUserWritePerm() {
        return getCurrentUserRoles().stream().map((r) -> r.getPermission().getPermissionName()).collect(Collectors.toList()).contains("w")
                || isCurrentUserAdmin() || isCurrentUserAppAdmin();
    }

    /**
     * Returns whether the currently logged-in user has read-permissions.
     * @return whether the currently logged-in user has read-permissions.
     */
    public boolean hasCurrentUserReadPerm() {
        return getCurrentUserRoles().stream().map((r) -> r.getPermission().getPermissionName()).collect(Collectors.toList()).contains("r")
               || hasCurrentUserWritePerm();
    }

    /**
     * Returns the user-details of the currently logged-in user.
     * @return the user-details of the currently logged-in user.
     */
    private ContracktorUserDetails getPrincipal() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            return (ContracktorUserDetails) auth.getPrincipal();
    }

}
