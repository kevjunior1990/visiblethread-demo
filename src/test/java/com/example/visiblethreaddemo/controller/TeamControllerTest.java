package com.example.visiblethreaddemo.controller;

import com.example.visiblethreaddemo.entity.Team;
import com.example.visiblethreaddemo.service.TeamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TeamControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TeamService teamService;

    @InjectMocks
    private TeamController teamController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(teamController).build();
    }

    @Test
    void testGetTeams_ShouldReturnListOfTeams() throws Exception {
        // Arrange
        Team team1 = new Team(1L, "Engineering");
        Team team2 = new Team(2L, "Marketing");
        List<Team> mockTeams = List.of(team1, team2);

        when(teamService.getTeams()).thenReturn(mockTeams);

        // Act & Assert
        mockMvc.perform(get("/api/v1/team/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("Engineering"))
                .andExpect(jsonPath("$[1].name").value("Marketing"));

        verify(teamService, times(1)).getTeams();
    }

    @Test
    void testGetTeamByName_ShouldReturnTeam() throws Exception {
        // Arrange
        String teamName = "Engineering";
        Team mockTeam = new Team(1L, teamName);

        when(teamService.getTeam(teamName)).thenReturn(mockTeam);

        // Act & Assert
        mockMvc.perform(get("/api/v1/team/{name}", teamName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(teamName));

        verify(teamService, times(1)).getTeam(teamName);
    }

    @Test
    void testAddTeam_ShouldReturnCreatedTeam() throws Exception {
        // Arrange
        String teamJson = "{\"name\": \"Engineering\"}";
        Team mockTeam = new Team(1L, "Engineering");

        when(teamService.saveTeam(any(Team.class))).thenReturn(mockTeam);

        // Act & Assert
        mockMvc.perform(post("/api/v1/team/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(teamJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Engineering"));

        verify(teamService, times(1)).saveTeam(any(Team.class));
    }
}
