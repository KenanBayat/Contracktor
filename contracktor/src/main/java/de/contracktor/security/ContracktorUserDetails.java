package de.contracktor.security;

import de.contracktor.model.User;
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

    public ContracktorUserDetails(User user) {
        this.username = Integer.toString(user.getLoginID());
        this.password = user.getPassword();

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
}
