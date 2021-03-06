package com.example.demo.controller;

import com.example.demo.SareetaApplication;
import com.example.demo.controllers.UserController;
import com.example.demo.model.persistence.User;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SareetaApplication.class)
public class UserControllerTests {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserController userController;

    @Before
    public void setup() {
    }

    @Test
    public void createUserHappyPath() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("User01");
        request.setPassword("012345678");
        request.setPasswordConfirm("012345678");

        ResponseEntity<User> responseEntity = userController.createUser(request);
        User user = responseEntity.getBody();

        Assert.assertTrue(user.getId() != null);
        Assert.assertTrue(request.getUsername().equals(user.getUsername()));
        Assert.assertTrue(bCryptPasswordEncoder
                .matches(request.getPassword(), user.getPassword()));
    }

    @Test
    public void minimumPasswordCharLengthError() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("User02");
        request.setPassword("123456");
        request.setPasswordConfirm("123456");

        ResponseEntity<User> responseEntity = userController.createUser(request);
        User user = responseEntity.getBody();

        Assert.assertNull(responseEntity.getBody());
        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void passwordMissmatchCharLengthError() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("User03");
        request.setPassword("123456789");
        request.setPasswordConfirm("1234567abcdef");

        ResponseEntity<User> responseEntity = userController.createUser(request);
        User user = responseEntity.getBody();

        Assert.assertNull(responseEntity.getBody());
        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void getUserById() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("UserById");
        request.setPassword("123456789");
        request.setPasswordConfirm("123456789");

        ResponseEntity<User> responseEntity = userController.createUser(request);
        User user = responseEntity.getBody();

        Assert.assertNotNull(responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        responseEntity = userController.findById(user.getId());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        User foundBy = responseEntity.getBody();
        Assert.assertEquals(user.getId(), foundBy.getId());

    }

    @Test
    public void getUsetByName() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("UserByName");
        request.setPassword("123456789");
        request.setPasswordConfirm("123456789");

        ResponseEntity<User> responseEntity = userController.createUser(request);
        User user = responseEntity.getBody();

        Assert.assertNotNull(responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        responseEntity = userController.findByUserName(user.getUsername());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        User foundBy = responseEntity.getBody();
        Assert.assertEquals(user.getUsername(), foundBy.getUsername());

    }
}
