package com.example.rest.domain.controller;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.rest.domain.model.User;
import com.example.rest.domain.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
 public class UserControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Mock
	private UserServiceImpl userServiceImpl;
	
	@InjectMocks
	private UserController userController;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	private User testUser;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
		testUser = new User(1L, "uetak", 28, "Osaka");
	}
	
	
	


}

