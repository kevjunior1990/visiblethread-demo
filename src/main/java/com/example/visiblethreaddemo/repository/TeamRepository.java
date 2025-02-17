package com.example.visiblethreaddemo.repository;

import com.example.visiblethreaddemo.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {

    Optional<Team> findByName(String name);

    List<Team> findAllByNameIn(List<String> names);

    Boolean existsByName(String name);
}
