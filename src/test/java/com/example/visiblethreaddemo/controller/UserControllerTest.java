package com.example.visiblethreaddemo.controller;

import com.example.visiblethreaddemo.entity.User;
import com.example.visiblethreaddemo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testGetUsers_ShouldReturnListOfUsers() throws Exception {
        // Arrange
        User user1 = new User();
        user1.setEmail("user1@example.com");
        User user2 = new User();
        user2.setEmail("user2@example.com");
        List<User> mockUsers = List.of(user1, user2);

        long startDate = 1700000000000L;
        long endDate = 1800000000000L;

        when(userService.getUsersWithoutDocuments(startDate, endDate)).thenReturn(mockUsers);

        // Act & Assert
        mockMvc.perform(get("/api/v1/user/")
                        .param("startDate", String.valueOf(startDate))
                        .param("endDate", String.valueOf(endDate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].email").value("user1@example.com"))
                .andExpect(jsonPath("$[1].email").value("user2@example.com"));

        verify(userService, times(1)).getUsersWithoutDocuments(startDate, endDate);
    }

    @Test
    void testAddUser_ShouldReturnCreatedUser() throws Exception {
        // Arrange
        String userJson = "{\"email\": \"user@example.com\"}";
        User mockUser = new User();
        mockUser.setEmail("user@example.com");

        when(userService.createUser(any(User.class))).thenReturn(mockUser);

        // Act & Assert
        mockMvc.perform(post("/api/v1/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("user@example.com"));

        verify(userService, times(1)).createUser(any(User.class));
    }

    @Test
    void testUpdateUser_ShouldReturnUpdatedUser() throws Exception {
        // Arrange
        String userJson = "{\"email\": \"updated@example.com\"}";
        User mockUser = new User();
        mockUser.setEmail("updated@example.com");

        when(userService.updateUser(any(User.class))).thenReturn(mockUser);

        // Act & Assert
        mockMvc.perform(put("/api/v1/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("updated@example.com"));

        verify(userService, times(1)).updateUser(any(User.class));
    }

    @Test
    void testDeleteUser_ShouldReturnNoContent() throws Exception {
        // Arrange
        String email = "user@example.com";

        doNothing().when(userService).deleteUser(email);

        // Act & Assert
        mockMvc.perform(delete("/api/v1/user/")
                        .param("email", email))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).deleteUser(email);
    }
}
