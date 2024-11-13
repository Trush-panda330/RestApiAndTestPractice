package com.example.rest.domain.controller;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.rest.domain.model.User;
import com.example.rest.domain.service.UserServiceImpl;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {


    @MockBean
    private UserServiceImpl userService;

    @Test
    void testFindAllUsers() throws Exception {
        // Mockデータ
        List<User> users = List.of(new User(1L, "ueda", 28, "Osaka"));
        when(userService.findAllUser()).thenReturn(users);


    }

}

