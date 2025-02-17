package com.example.visiblethreaddemo.repository;

import com.example.visiblethreaddemo.entity.Team;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
class TeamRepositoryTest {

    @Autowired
    private TeamRepository teamRepository;

    private Team team1, team2;

    @BeforeEach
    void setUp() {
        team1 = Team.builder()
                .name("Development")
                .build();

        team2 = Team.builder()
                .name("Marketing")
                .build();

        teamRepository.saveAll(List.of(team1, team2));
    }

    @AfterEach
    void tearDown() {
        teamRepository.deleteAll();
    }

    @Test
    void testSaveAndFindById() {
        // Arrange
        Team newTeam = Team.builder()
                .name("Finance")
                .build();

        // Act
        Team savedTeam = teamRepository.save(newTeam);
        Optional<Team> retrievedTeam = teamRepository.findById(savedTeam.getId());

        // Assert
        assertTrue(retrievedTeam.isPresent());
        assertThat(retrievedTeam.get().getName()).isEqualTo("Finance");
    }

    @Test
    void testFindByName() {
        // Act
        Optional<Team> foundTeam = teamRepository.findByName("Development");

        // Assert
        assertThat(foundTeam).isPresent();
        assertThat(foundTeam.get().getName()).isEqualTo("Development");
    }

    @Test
    void testExistsByName() {
        // Act & Assert
        assertThat(teamRepository.existsByName("Marketing")).isTrue();
        assertThat(teamRepository.existsByName("NonExistentTeam")).isFalse();
    }

    @Test
    void testFindAll() {
        // Act
        List<Team> teams = teamRepository.findAll();

        // Assert
        assertThat(teams).hasSize(2);
    }

    @Test
    void testDeleteTeam() {
        // Act
        teamRepository.delete(team1);

        // Assert
        Optional<Team> deletedTeam = teamRepository.findById(team1.getId());
        assertThat(deletedTeam).isEmpty();
        assertThat(teamRepository.findAll()).hasSize(1);
    }
}
