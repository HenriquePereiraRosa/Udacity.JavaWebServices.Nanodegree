package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HashService hashService;

    public Boolean isUsernameAvailable(String username) {
        return (userMapper.getUserByUsername(username) == null);
    }

    public Integer createUser(User user) {
        String encodedSalt = hashService.createEncodedSalt();
        String hashedPass = hashService.getHashedValue(user.getPassword(), encodedSalt);
        Integer id =  userMapper.insertUser(new User(null,
                user.getUsername(),
                encodedSalt,
                hashedPass,
                user.getFirstname(),
                user.getLastname()));
        logger.info("User saved: " + id);
        return id;
    }

    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }
}
