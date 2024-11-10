package com.example.rest.domain.service;

import java.util.List;

import com.example.rest.domain.model.User;

public interface UserServiceIF {

	List<User> findAllUser();
	
	User findByIdUser(Long id);
	
	int  insertUser(User user);
	
	int updateUser(User user);
	
	int deleteUser(Long id);
}
