package de.contracktor.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class APIAuthorizationFilter extends BasicAuthenticationFilter {

    public APIAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }



}
