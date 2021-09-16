package de.contracktor.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.www.BasicAuthenticationConverter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Filter for creating API-Tokens for app-clients that successfully authenticate.
 */
public class APITokenFilter extends BasicAuthenticationFilter {

    private BasicAuthenticationConverter authenticationConverter = new BasicAuthenticationConverter();

    public APITokenFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException {
        Algorithm encoder = Algorithm.HMAC256("test".getBytes());
        String token = JWT.create()
                .withSubject(authResult.getName())
                .withIssuer("ContracktorWEB")
                .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                .withClaim("authorities", authResult.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(encoder);
        response.setHeader("api_token", token);
        response.setStatus(200);
        response.setContentType("application/json");
        String organisation = ((ContracktorUserDetails) authResult.getPrincipal()).getOrganisationName();
        List<String> content = new ArrayList<>(List.of(token,organisation));
        new ObjectMapper().writeValue(response.getOutputStream(), content);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(authenticationConverter.convert(request) != null) {
            super.doFilterInternal(request, response, chain);
        } else {
            onUnsuccessfulAuthentication(request,response,new AuthenticationCredentialsNotFoundException("Authorization header missing or malformed."));
        }
    }

    @Override
    protected void onUnsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        super.onUnsuccessfulAuthentication(request,response,failed);
        response.setStatus(401);
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        return !path.startsWith("/api/login");
    }
}
