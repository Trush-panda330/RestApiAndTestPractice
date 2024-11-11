package com.example.rest.domain.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rest.domain.model.User;
import com.example.rest.domain.response.ApiUserResponse;
import com.example.rest.domain.service.UserServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
	
	private final UserServiceImpl userService;
	
	@GetMapping
	public ResponseEntity<ApiUserResponse<List<User>>> findAll() {
		List<User> users = userService.findAllUser();
		ApiUserResponse<List<User>> response = new ApiUserResponse<List<User>>(users, "Users feched successfully", true);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> findById(@PathVariable Long id) {
		User user = userService.findByIdUser(id);
		return ResponseEntity.ok(user);
	}
	
	@PostMapping("/user")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		int result = userService.insertUser(user);
		if(result > 0) {
			return ResponseEntity.status(HttpStatus.CREATED).body(user);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		 int result = userService.deleteUser(id);
		 if(result > 0) {
			 return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		 }else {
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		 }
	}

}
