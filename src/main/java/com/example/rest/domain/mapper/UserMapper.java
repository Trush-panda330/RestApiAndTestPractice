package com.example.rest.domain.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.rest.domain.model.User;

@Mapper
public interface UserMapper {
	
	List<User> findAll();
	
	User findById(@Param("id") Long id);
	
	int insert(@Param("user") User user);
	
	int update(@Param("user") User user);
	
	int deleteById(@Param("id") Long id);

}
