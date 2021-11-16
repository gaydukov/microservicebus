package com.microservice.microservicebus.service.serviceimpl;

import com.microservice.microservicebus.entity.User;
import com.microservice.microservicebus.repository.RedisRepository;
import com.microservice.microservicebus.service.RedisService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class RedisServiceImpl implements RedisService {
    private final RedisRepository repository;
    @Override
    public List<User> getAllUsers() {
        List<User> users=repository.findAll();
        if (users.isEmpty()){
            System.out.println("User list is empty");
        }
        return users;
    }

    @Override
    public User getUserById(Long id) {
        User user=repository.findById(id);
        if (user==null){
            System.out.println("User not found");
        }
        return user;
    }

    @Override
    public User saveUser(User user) {
        return repository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        if (repository.findById(id)==null) {
            System.out.println("User not found");
        }
        System.out.println(repository.deleteUser(id));
    }

}
