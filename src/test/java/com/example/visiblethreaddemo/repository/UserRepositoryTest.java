package com.example.visiblethreaddemo.repository;

import com.example.visiblethreaddemo.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User oldUser;
    private User recentUser;

    @BeforeEach
    void setUp() {
        // Create an old user
        oldUser = new User();
        oldUser.setEmail("old.email@example.com");
        oldUser.setCreatedDate(Timestamp.from(LocalDateTime.now().minusDays(10).toInstant(ZoneOffset.UTC))); // 10 days ago
        userRepository.save(oldUser);

        // Create a recent user
        recentUser = new User();
        recentUser.setEmail("recent.user@example.com");
        recentUser.setCreatedDate(Timestamp.from(LocalDateTime.now().minusDays(2).toInstant(ZoneOffset.UTC))); // 2 days ago
        userRepository.save(recentUser);
    }

    @Test
    void testFindByCreatedDateBefore() {
        // Define cutoff date (5 days ago)
        Timestamp cutoffDate = Timestamp.from(LocalDateTime.now().minusDays(5).toInstant(ZoneOffset.UTC));

        // Call repository method
        List<User> users = userRepository.findByCreatedDateBefore(cutoffDate);

        // Assertions
        assertThat(users).isNotEmpty();
        assertThat(users).contains(oldUser);
        assertThat(users).doesNotContain(recentUser);
    }
}
