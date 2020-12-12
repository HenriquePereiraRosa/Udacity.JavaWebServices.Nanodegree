package com.udacity.jwdnd.c1.javawebdev.service;

import com.udacity.jwdnd.c1.javawebdev.mapper.UserMapper;
import com.udacity.jwdnd.c1.javawebdev.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthenticationService implements AuthenticationProvider {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private HashService hashService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = userMapper.getUserByEmail(email);
        if(user != null) {
            String encodedSalt = user.getSalt();
            String hashedPassword = hashService.getHashedValue(password, user.getSalt());
            if(user.getPassword().equals(hashedPassword)) {
                return new UsernamePasswordAuthenticationToken(email, password, new ArrayList<>());
            }

        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
