package com.microservice.microservicebus.service;

import com.microservice.microservicebus.entity.User;

import java.util.List;

public interface RedisService {
    List<User> getAllUsers();

    User getUserById(Long id);

    User saveUser(User user);

    void deleteUser(Long id);
}
