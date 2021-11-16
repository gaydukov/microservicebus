package com.microservice.microservicebus.service.serviceimpl;

import com.microservice.microservicebus.entity.User;
import com.microservice.microservicebus.repository.UserRepository;
import com.microservice.microservicebus.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    @RabbitListener(queues = "${rabbitmq.queue1}")
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            System.out.println("Users list is empty");
        }
        return users;
    }

    @Override
    @RabbitListener(queues = "${rabbitmq.queue2}")
    public User getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            System.out.println("User not found");
        }
        return user;
    }

    @Override
    @RabbitListener(queues = "${rabbitmq.queue}")
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    @RabbitListener(queues = "${rabbitmq.queue3}")
    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            System.out.println("User not found");
        }
        userRepository.deleteById(id);
    }

}
