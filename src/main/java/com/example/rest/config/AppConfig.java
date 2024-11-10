package com.example.rest.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.example.rest.domain.mapper.UserMapper")
public class AppConfig {
	
}
