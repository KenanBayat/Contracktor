package de.contracktor.security;

import de.contracktor.model.Role;
import de.contracktor.model.UserAccount;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ContracktorUserDetails implements UserDetails {


    private String username;
    private String password;
    private List<GrantedAuthority> authorities;
    private boolean isAppAdmin;
    private boolean isAdmin;
    private String organisationName;
    private List<Role> roles;
    private int organisationId;

    public ContracktorUserDetails(UserAccount user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.roles = user.getRoles();
        this.isAppAdmin = user.getIsAdmin();
        this.isAdmin = user.getIsAdmin();
        this.organisationName = user.getOrganisation().getOrganisationName();
        this.organisationId = user.getOrganisation().getId();

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));

        if (user.getIsAdmin()) {
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
        }

        if (user.getIsApplicationAdmin()) {
            authorities.add(new SimpleGrantedAuthority("APP_ADMIN"));
        }

        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public boolean isAppAdmin() {
        return isAppAdmin;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public String getOrganisationName() {
        return organisationName;
    }

    public int getOrganisationId() {
        return organisationId;
    }
}

