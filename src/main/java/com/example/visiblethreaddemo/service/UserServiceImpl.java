package com.example.visiblethreaddemo.service;

import com.example.visiblethreaddemo.entity.Team;
import com.example.visiblethreaddemo.entity.User;
import com.example.visiblethreaddemo.exception.UserNotFoundException;
import com.example.visiblethreaddemo.exception.UserServiceException;
import com.example.visiblethreaddemo.repository.DocumentRepository;
import com.example.visiblethreaddemo.repository.TeamRepository;
import com.example.visiblethreaddemo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private TeamRepository teamRepository;


    @Override
    public User createUser(User user) {

        var teams = getUserTeams(user.getTeamNames());

        try {

            user.getTeams().addAll(teams);

            return userRepository.save(user);

        } catch (Exception e) {

            log.error("Error creating user: {}", e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }

    @Override
    public User updateUser(User updatedUser) {


        var teams = getUserTeams(updatedUser.getTeamNames());

        return userRepository.findByEmail(updatedUser.getEmail())
                .map(user -> {
                    user.setEmail(updatedUser.getEmail());
                    user.getTeams().clear();
                    user.getTeams().addAll(teams);
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new UserNotFoundException(updatedUser.getEmail()));
    }

    @Override
    public void deleteUser(String email) {

        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.getTeams().clear();

        userRepository.delete(user);
    }

    @Override
    public List<User> getUsersWithoutDocuments(Long startDate, Long endDate) {

        return userRepository.findByCreatedDateBefore(new Timestamp(endDate))
                .stream()
                .filter(user -> !documentRepository.existsByUserIdAndCreatedDateBetween(
                                user.getId(),
                                new Timestamp(startDate),
                                new Timestamp(endDate)
                        )
                )
                .collect(Collectors.toList());
    }

    private List<Team> getUserTeams(List<String> names) {

        if (names == null || names.isEmpty()) {

            throw new UserServiceException("Please add at least one name to payload. User must be linked to at least one team.");
        }

        var teams = teamRepository.findAllByNameIn(names);


        if (teams.isEmpty()) {
            throw new UserServiceException("No teams found.");
        }

        return teams;
    }
}
