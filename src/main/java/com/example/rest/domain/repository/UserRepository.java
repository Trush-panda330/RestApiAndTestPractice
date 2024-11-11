package com.example.rest.domain.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.rest.domain.mapper.UserMapper;
import com.example.rest.domain.model.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class UserRepository {
	
	private final UserMapper userMapper;
	
	public List<User> findAll() {
		return userMapper.findAll();
	}
	
	public User findByIdUser(Long id) {
		return userMapper.findById(id);
	}
	
	public int create(User user) {
		return userMapper.insert(user);
	}
	
	public int update(User user) {
		return userMapper.update(user.getId(),user);
	}
	
	public int deleteById(Long id) {
		return userMapper.deleteById(id);
	}
}
