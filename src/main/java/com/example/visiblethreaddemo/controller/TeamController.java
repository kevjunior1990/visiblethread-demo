package com.example.visiblethreaddemo.controller;

import com.example.visiblethreaddemo.entity.Team;
import com.example.visiblethreaddemo.service.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/team/", produces = "application/json")
@Slf4j
public class TeamController {

    @Autowired
    private TeamService service;

    @GetMapping
    public ResponseEntity<List<Team>> getTeams() {

        return ResponseEntity.ok(service.getTeams());
    }

    @GetMapping("/{name}")
    public ResponseEntity<Team> getTeam(@PathVariable String name) {

        return ResponseEntity.ok(service.getTeam(name));
    }

    @PostMapping
    public ResponseEntity<Team> addTeam(@RequestBody Team team) {

        return ResponseEntity.ok(service.saveTeam(team));
    }
}
