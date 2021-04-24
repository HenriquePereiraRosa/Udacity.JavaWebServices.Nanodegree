package com.example.demo.controller;

import com.example.demo.SareetaApplication;
import com.example.demo.controllers.UserController;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.utils.TestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SareetaApplication.class)
public class UserControllerTestsWithMock {

    private UserController userController;
    private UserRepository userRepo = mock(UserRepository.class);
    private CartRepository cartRepo = mock(CartRepository.class);
    private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

    @Before
    public void setup() {
        userController = new UserController();
        TestUtils.injectObjects(userController, "userRepository", userRepo);
        TestUtils.injectObjects(userController, "cartRepository", cartRepo);
        TestUtils.injectObjects(userController, "bCryptPasswordEncoder", encoder);
    }

    @Test
    public void createUserHappyPathWithMock() {
        when(encoder.encode("testPass")).thenReturn("thisIsHashed");
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("User02");
        request.setPassword("testPass");
        request.setPasswordConfirm("testPass");

        ResponseEntity<User> responseEntity = userController.createUser(request);
        User user = responseEntity.getBody();

        Assert.assertTrue(user.getId() == null);
        Assert.assertTrue(request.getUsername().equals(user.getUsername()));
        Assert.assertEquals("thisIsHashed", user.getPassword());
    }
}
