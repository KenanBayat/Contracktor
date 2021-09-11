package de.contracktor.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.header.Header;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpHeaders;
import java.util.List;
import java.util.stream.Collectors;

public class APIAuthorizationFilter extends BasicAuthenticationFilter {

    public APIAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request.getServletPath().equals("/api/login")) {
            chain.doFilter(request,response);
        } else {
            String auth = request.getHeader("authorization");

            if (auth.startsWith("Bearer ")) {
                auth = auth.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("test".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(auth);
                String username = decodedJWT.getSubject();
                List<String> roles = decodedJWT.getClaim("authorities").asList(String.class);
                List<SimpleGrantedAuthority> authorities = roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
            chain.doFilter(request, response);
        }
    }
}
