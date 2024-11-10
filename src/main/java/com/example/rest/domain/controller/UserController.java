package com.example.rest.domain.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rest.domain.model.User;
import com.example.rest.domain.service.UserServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {
	
	private final UserServiceImpl userService;
	
	@GetMapping
	public List<User> findAll() {
		return userService.findAllUser();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> findById(@PathVariable Long id) {
		User user = userService.findByIdUser(id);
		return ResponseEntity.ok(user);
	}

}
