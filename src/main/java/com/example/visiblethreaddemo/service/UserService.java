package com.example.visiblethreaddemo.service;

import com.example.visiblethreaddemo.entity.User;

import java.util.List;

public interface UserService {

    User createUser(User user);

    User updateUser(User updatedUser);

    void deleteUser(String email);

    List<User> getUsersWithoutDocuments(Long startDate, Long endDate);
}
