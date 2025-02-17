package com.example.visiblethreaddemo.service;

import com.example.visiblethreaddemo.entity.Team;
import com.example.visiblethreaddemo.exception.TeamNameNotFoundException;
import com.example.visiblethreaddemo.exception.TeamServiceException;
import com.example.visiblethreaddemo.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeamServiceImplTest {

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private TeamServiceImpl teamService;

    private Team team;

    @BeforeEach
    void setUp() {
        team = new Team();
        team.setName("Test Team");
    }

    @Test
    void getTeams_ShouldReturnTeamList() {
        when(teamRepository.findAll()).thenReturn(List.of(team));

        List<Team> teams = teamService.getTeams();

        assertFalse(teams.isEmpty());
        assertEquals(1, teams.size());
        assertEquals("Test Team", teams.get(0).getName());
    }

    @Test
    void getTeam_WhenExists_ShouldReturnTeam() {
        when(teamRepository.findByName("Test Team")).thenReturn(Optional.of(team));

        Team foundTeam = teamService.getTeam("Test Team");

        assertNotNull(foundTeam);
        assertEquals("Test Team", foundTeam.getName());
    }

    @Test
    void getTeam_WhenNotExists_ShouldThrowException() {
        when(teamRepository.findByName("Unknown Team")).thenReturn(Optional.empty());

        assertThrows(TeamNameNotFoundException.class, () -> teamService.getTeam("Unknown Team"));
    }

    @Test
    void saveTeam_WhenSuccessful_ShouldReturnSavedTeam() {
        when(teamRepository.save(team)).thenReturn(team);

        Team savedTeam = teamService.saveTeam(team);

        assertNotNull(savedTeam);
        assertEquals("Test Team", savedTeam.getName());
    }

    @Test
    void saveTeam_WhenExceptionThrown_ShouldThrowServiceException() {
        when(teamRepository.save(any(Team.class))).thenThrow(new RuntimeException("Database Error"));

        assertThrows(TeamServiceException.class, () -> teamService.saveTeam(new Team()));
    }
}

