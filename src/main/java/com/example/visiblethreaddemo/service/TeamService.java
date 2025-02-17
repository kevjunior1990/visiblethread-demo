package com.example.visiblethreaddemo.service;

import com.example.visiblethreaddemo.entity.Team;

import java.util.List;

public interface TeamService {

    List<Team> getTeams();

    Team getTeam(String name);

    Team saveTeam(Team team);
}
