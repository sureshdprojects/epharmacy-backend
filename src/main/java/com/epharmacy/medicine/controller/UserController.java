package com.epharmacy.medicine.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.epharmacy.medicine.model.User;
import com.epharmacy.medicine.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/user/signup")
	public ResponseEntity<Object> createUser(@RequestBody @Valid User user) {
		return userService.processCreateRequest(user);
	}
	
	@PostMapping("/userlogin")
	public ResponseEntity<Object> verifyUser(@RequestBody User user) {
		return  userService.validateCredentials(user);
	}
	
	@GetMapping("/dummy")
	public String get() {
		return "working";
	}
	
}
