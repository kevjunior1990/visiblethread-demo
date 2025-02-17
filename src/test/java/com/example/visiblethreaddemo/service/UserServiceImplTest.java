package com.example.visiblethreaddemo.service;

import com.example.visiblethreaddemo.entity.Team;
import com.example.visiblethreaddemo.entity.User;
import com.example.visiblethreaddemo.exception.UserNotFoundException;
import com.example.visiblethreaddemo.exception.UserServiceException;
import com.example.visiblethreaddemo.repository.DocumentRepository;
import com.example.visiblethreaddemo.repository.TeamRepository;
import com.example.visiblethreaddemo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser_ShouldReturnSavedUser() {
        // Arrange
        User user = new User();
        user.setEmail("test@example.com");
        user.setTeamNames(List.of("Team A"));

        Team team = new Team();
        team.setName("Team A");

        when(teamRepository.findAllByNameIn(user.getTeamNames())).thenReturn(List.of(team));
        when(userRepository.save(user)).thenReturn(user);

        // Act
        User createdUser = userService.createUser(user);

        // Assert
        assertNotNull(createdUser);
        assertEquals(user.getEmail(), createdUser.getEmail());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testCreateUser_ShouldThrowException_WhenNoTeamsFound() {
        // Arrange
        User user = new User();
        user.setEmail("test@example.com");
        user.setTeamNames(List.of("NonExistentTeam"));

        when(teamRepository.findAllByNameIn(user.getTeamNames())).thenReturn(List.of());

        // Act & Assert
        UserServiceException exception = assertThrows(UserServiceException.class, () -> userService.createUser(user));
        assertEquals("User Service failed with error message:  No teams found.", exception.getMessage());
    }

    @Test
    void testUpdateUser_ShouldReturnUpdatedUser() {
        // Arrange
        User existingUser = new User();
        existingUser.setEmail("test@example.com");

        User updatedUser = new User();
        updatedUser.setEmail("test@example.com");
        updatedUser.setTeamNames(List.of("Team A"));

        Team team = new Team();
        team.setName("Team A");

        when(userRepository.findByEmail(updatedUser.getEmail())).thenReturn(Optional.of(existingUser));
        when(teamRepository.findAllByNameIn(updatedUser.getTeamNames())).thenReturn(List.of(team));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        // Act
        User result = userService.updateUser(updatedUser);

        // Assert
        assertNotNull(result);
        assertEquals(updatedUser.getEmail(), result.getEmail());
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void testUpdateUser_ShouldThrowUserNotFoundException() {
        // Arrange
        User updatedUser = new User();
        updatedUser.setEmail("notfound@example.com");

        when(userRepository.findByEmail(updatedUser.getEmail())).thenReturn(Optional.empty());

        // Act & Assert
        UserServiceException exception = assertThrows(UserServiceException.class, () -> userService.updateUser(updatedUser));
        assertEquals("User Service failed with error message:  Please add at least one name to payload. User must be linked to at least one team.", exception.getMessage());
    }

    @Test
    void testDeleteUser_ShouldDeleteUser() {
        // Arrange
        User user = new User();
        user.setEmail("test@example.com");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        // Act
        userService.deleteUser(user.getEmail());

        // Assert
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void testDeleteUser_ShouldThrowException_WhenUserNotFound() {
        // Arrange
        when(userRepository.findByEmail("notfound@example.com")).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.deleteUser("notfound@example.com"));
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testGetUsersWithoutDocuments_ShouldReturnUsers() {
        // Arrange
        long startDate = System.currentTimeMillis() - (10 * 24 * 60 * 60 * 1000);
        long endDate = System.currentTimeMillis();

        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        when(userRepository.findByCreatedDateBefore(new Timestamp(endDate))).thenReturn(List.of(user));
        when(documentRepository.existsByUserIdAndCreatedDateBetween(user.getId(), new Timestamp(startDate), new Timestamp(endDate)))
                .thenReturn(false);

        // Act
        List<User> users = userService.getUsersWithoutDocuments(startDate, endDate);

        // Assert
        assertEquals(1, users.size());
        assertEquals("test@example.com", users.get(0).getEmail());
    }
}
