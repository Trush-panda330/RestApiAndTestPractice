package com.example.rest.domain.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.rest.domain.exception.UserNotFoundException;
import com.example.rest.domain.model.User;
import com.example.rest.domain.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserServiceImpl userService;

	private ObjectMapper objectMapper = new ObjectMapper();

	private User testUser;

	@BeforeEach
	void setUp() {
		testUser = new User(1L, "uetak", 28, "Osaka");
	}

	@Test
	void testFindAll() throws Exception {
		when(userService.findAllUser()).thenReturn(List.of(testUser));

		mockMvc.perform(get("/user"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.data[0].name").value("uetak"));
	}

	@Test
	void testFindById_success() throws Exception {
		when(userService.findByIdUser(1L)).thenReturn(testUser);

		mockMvc.perform(get("/user/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.data.name").value("uetak"));

	}

	@Test
	void testFindById_NotFound() throws Exception {
		when(userService.findByIdUser(1L)).thenThrow(new UserNotFoundException("ID:1のユーザーは存在しません"));

		mockMvc.perform(get("/user/1"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.success").value(false));
	}

	@Test
	void testCreateUser_Success() throws Exception {
		when(userService.insertUser(any(User.class))).thenReturn(1);

		mockMvc.perform(post("/user")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(testUser)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$..data.name").value("uetak"));
	}

	@Test
	void testUpdateUser_Success() throws Exception {
		when(userService.updateUser(any(User.class))).thenReturn(1);

		mockMvc.perform(put("/user/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(testUser)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.data.name").value("uetak"));
	}

	@Test
	void testUpdateUser_NotFound() throws Exception {
		when(userService.updateUser(any(User.class))).thenReturn(0);

		mockMvc.perform(put("/user/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(testUser)))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.success").value(false));
	}

	@Test
	void testDeleteUser_Success() throws Exception {
		when(userService.deleteUser(1L)).thenReturn(1);

		mockMvc.perform(delete("/user/1"))
				.andExpect(status().isNoContent());
	}

	@Test
	void testDeleteUser_NotFound() throws Exception {
		when(userService.deleteUser(1L)).thenReturn(0);

		mockMvc.perform(delete("/user/1"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.success").value(false));
	}
}
