package com.example.rest.domain.service;

import java.util.List;

import com.example.rest.domain.model.User;

public interface UserServiceIF {

	List<User> findAllUser();
	
	User findByIdUser(Long id);
	
	void insertUser(User user);
	
	void updateUser(User user);
	
	void deleteUser(Long id);
}
