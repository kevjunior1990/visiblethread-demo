package com.example.visiblethreaddemo.controller;

import com.example.visiblethreaddemo.entity.User;
import com.example.visiblethreaddemo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/user/", produces = "application/json")
@Slf4j
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<List<User>> getUsers(
            @RequestParam("startDate") Long startDate,
            @RequestParam("endDate") Long endDate
    ) {

        return ResponseEntity.ok(service.getUsersWithoutDocuments(startDate, endDate));
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {

        return ResponseEntity.ok(service.createUser(user));
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) {

        return ResponseEntity.ok(service.updateUser(user));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestParam("email") String email) {

        service.deleteUser(email);
        return ResponseEntity.noContent().build();
    }
}
