package com.example.demo.controller;

import com.example.demo.SareetaApplication;
import com.example.demo.controllers.CartController;
import com.example.demo.controllers.OrderController;
import com.example.demo.controllers.UserController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.model.requests.ModifyCartRequest;
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

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SareetaApplication.class)
public class CartControllerTests {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserController userController;
    @Autowired
    private CartController cartController;
    @Autowired
    private OrderController orderController;

    @Before
    public void setup() {
    }

    @Test
    public void addToCartHappyPath() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("UserCart01");
        request.setPassword("12345678");
        request.setPasswordConfirm("12345678");

        ResponseEntity<User> responseEntity = userController.createUser(request);
        User user = responseEntity.getBody();

        Assert.assertNotNull(responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        responseEntity = userController.findByUserName(user.getUsername());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        User foundBy = responseEntity.getBody();
        Assert.assertEquals(user.getUsername(), foundBy.getUsername());

        ModifyCartRequest cartRequest = new ModifyCartRequest();
        cartRequest.setUsername(user.getUsername());
        cartRequest.setItemId(1L);
        cartRequest.setQuantity(3);
        ResponseEntity<Cart> cartEntity = cartController.addTocart(cartRequest);
        Assert.assertNotNull(cartEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, cartEntity.getStatusCode());

        cartRequest.setItemId(2L);
        cartEntity = cartController.addTocart(cartRequest);
        Assert.assertNotNull(cartEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, cartEntity.getStatusCode());

        cartEntity = cartController.removeFromcart(cartRequest);
        Assert.assertNotNull(cartEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, cartEntity.getStatusCode());
    }

    @Test
    public void addToCartWithNonExistentUser() {

        ModifyCartRequest cartRequest = new ModifyCartRequest();
        cartRequest.setUsername("UserCart02");
        cartRequest.setItemId(10L);
        cartRequest.setQuantity(3);
        ResponseEntity<Cart> cartEntity = cartController.addTocart(cartRequest);
        Assert.assertNull(cartEntity.getBody());
        Assert.assertEquals(HttpStatus.NOT_FOUND, cartEntity.getStatusCode());
    }

    @Test
    public void addToCartWithNonExistentItemId() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("UserCart03");
        request.setPassword("12345678");
        request.setPasswordConfirm("12345678");

        ResponseEntity<User> responseEntity = userController.createUser(request);
        User user = responseEntity.getBody();

        Assert.assertNotNull(responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        responseEntity = userController.findByUserName(user.getUsername());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        User foundBy = responseEntity.getBody();
        Assert.assertEquals(user.getUsername(), foundBy.getUsername());

        ModifyCartRequest cartRequest = new ModifyCartRequest();
        cartRequest.setUsername(user.getUsername());
        cartRequest.setItemId(10L);
        cartRequest.setQuantity(3);
        ResponseEntity<Cart> cartEntity = cartController.addTocart(cartRequest);
        Assert.assertNull(cartEntity.getBody());
        Assert.assertEquals(HttpStatus.NOT_FOUND, cartEntity.getStatusCode());
    }

    @Test
    public void removeFromCartWithNonExistentUser() {

        ModifyCartRequest cartRequest = new ModifyCartRequest();
        cartRequest.setUsername("UserCart04");
        cartRequest.setItemId(10L);
        cartRequest.setQuantity(3);
        ResponseEntity<Cart> cartEntity = cartController.removeFromcart(cartRequest);
        Assert.assertNull(cartEntity.getBody());
        Assert.assertEquals(HttpStatus.NOT_FOUND, cartEntity.getStatusCode());
    }

    @Test
    public void removeFromCartWithNonExistentItemId() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("UserCart05");
        request.setPassword("12345678");
        request.setPasswordConfirm("12345678");

        ResponseEntity<User> responseEntity = userController.createUser(request);
        User user = responseEntity.getBody();

        Assert.assertNotNull(responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        responseEntity = userController.findByUserName(user.getUsername());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        User foundBy = responseEntity.getBody();
        Assert.assertEquals(user.getUsername(), foundBy.getUsername());

        ModifyCartRequest cartRequest = new ModifyCartRequest();
        cartRequest.setUsername(user.getUsername());
        cartRequest.setItemId(10L);
        cartRequest.setQuantity(3);
        ResponseEntity<Cart> cartEntity = cartController.removeFromcart(cartRequest);
        Assert.assertNull(cartEntity.getBody());
        Assert.assertEquals(HttpStatus.NOT_FOUND, cartEntity.getStatusCode());
    }
}
