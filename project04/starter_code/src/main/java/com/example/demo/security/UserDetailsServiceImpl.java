package com.example.demo.security;

import com.example.demo.exception.custom.UserNotFoundException;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    Log log = LogFactory.getLog(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.error("ERROR - Username not found on login: "  +  username);
            throw new UserNotFoundException("ERROR - Username not found on login: "  +  username);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), Collections.emptyList());
    }
}