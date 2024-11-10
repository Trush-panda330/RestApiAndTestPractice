package com.example.rest.domain.repository;

import org.springframework.stereotype.Repository;

import com.example.rest.domain.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class UserRepository {
	
	private final UserMapper userMapper;
	
}
