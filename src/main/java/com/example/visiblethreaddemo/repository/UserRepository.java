package com.example.visiblethreaddemo.repository;

import com.example.visiblethreaddemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findByCreatedDateBefore(Timestamp date);
}
