package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

	Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping("/id/{id}")
	public ResponseEntity<User> findById(@PathVariable Long id) {
		return ResponseEntity.of(userRepository.findById(id));
	}
	
	@GetMapping("/username/{username}")
	public ResponseEntity<User> findByUserName(@PathVariable String username) {
		User user = userRepository.findByUsername(username);
		return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
	}
	
	@PostMapping("/create")
	public ResponseEntity<User> createUser(@RequestBody CreateUserRequest userReq) {

		log.info("Creating User: "  +  userReq.getUsername());

		User user = new User();
		user.setUsername(userReq.getUsername());
		if(userReq.getPassword() != null
				&& (userReq.getPasswordConfirm().length() < 7
				|| !userReq.getPassword().equals(userReq.getPasswordConfirm()))) {
			log.error("ERROR - with password from User: " + userReq.getUsername());
			return ResponseEntity.badRequest().build();
		}

		user.setPassword(bCryptPasswordEncoder.encode(userReq.getPassword()));

		Cart cart = new Cart();
		cartRepository.save(cart);
		user.setCart(cart);
		userRepository.save(user);

		log.info("User Saved! Id: "  +  user.getId());
		return ResponseEntity.ok(user);
	}
	
}
