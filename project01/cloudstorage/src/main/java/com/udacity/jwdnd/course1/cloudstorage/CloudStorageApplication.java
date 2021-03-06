package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.service.HashService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CloudStorageApplication {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;

    @Autowired
    private HashService hashService;

    public static void main(String[] args) {
        SpringApplication.run(CloudStorageApplication.class, args);
    }

    @Bean
    public void createUserTest() {
        // todo remove after release
        String encodedSalt = hashService.createEncodedSalt();
        String hashedPass = hashService.getHashedValue("123", encodedSalt);
        Integer id = userMapper.insertUser(new User(null,
                "t1",
                encodedSalt,
                hashedPass,
                "Test01",
                "Last Name"));
        logger.info("User test1 saved: " + id);
        id = userMapper.insertUser(new User(null,
                "t2",
                encodedSalt,
                hashedPass,
                "Test02",
                "Last Name"));
        logger.info("User test2 saved: " + id);
    }

}
