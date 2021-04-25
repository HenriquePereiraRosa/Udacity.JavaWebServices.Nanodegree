package com.example.demo.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.config.JWTCustomSecurityConstants;
import com.example.demo.model.persistence.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    Log log = LogFactory.getLog(this.getClass());
    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) {
        try {
            User credentials = new ObjectMapper()
                    .readValue(req.getInputStream(), User.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentials.getUsername(),
                            credentials.getPassword(),
                            new ArrayList<>()));
        } catch (AuthenticationException e) {
            logger.error("ERROR - Authentication failed. user " + e.getLocalizedMessage());
            throw new BadCredentialsException("invalid login info");
        } catch (IOException e) {
            log.error("ERROR -  IOException on auth", e);
            throw new RuntimeException(e);
        } catch (Exception e) {
            log.error("ERROR - Exception on auth", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        String token = JWT.create()
                .withSubject(((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JWTCustomSecurityConstants.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(JWTCustomSecurityConstants.SECRET.getBytes()));
        res.addHeader(JWTCustomSecurityConstants.HEADER_STRING, JWTCustomSecurityConstants.TOKEN_PREFIX + token);

        log.info("Logged in successfully:"  +  auth.getName());
    }
}
