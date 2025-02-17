package com.example.visiblethreaddemo.service;

import com.example.visiblethreaddemo.entity.Team;
import com.example.visiblethreaddemo.exception.TeamNameNotFoundException;
import com.example.visiblethreaddemo.exception.TeamServiceException;
import com.example.visiblethreaddemo.repository.TeamRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@Slf4j
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamRepository repository;

    @Override
    public List<Team> getTeams() {

        return repository.findAll();
    }

    @Override
    public Team getTeam(String name) {

        return repository.findByName(name).orElseThrow(() -> new TeamNameNotFoundException(name));
    }

    @Override
    public Team saveTeam(Team team) {

        try {

            return repository.save(team);

        } catch (Exception e) {

            throw new TeamServiceException(e.getMessage());
        }
    }
}
