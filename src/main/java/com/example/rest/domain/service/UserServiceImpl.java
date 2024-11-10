package com.example.rest.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.rest.domain.model.User;
import com.example.rest.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserServiceIF {
	
	private final UserRepository userRepository;

	@Override
	public List<User> findAllUser() {
		return userRepository.findAll();
	}

	@Override
	public User findByIdUser(Long id) {
		return userRepository.findByIdUser(id);
	}

	@Override
	public int insertUser(User user) {
		return userRepository.create(user);
	}

	@Override
	public int updateUser(User user) {
		return userRepository.update(user);
	}

	@Override
	public int deleteUser(Long id) {
		return userRepository.deleteById(id);
	}

}
