package com.microservice.microservicebus.controller;

import com.microservice.microservicebus.dto.UserDto;
import com.microservice.microservicebus.entity.User;
import com.microservice.microservicebus.mapper.UserMapper;
import com.microservice.microservicebus.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final RabbitTemplate rabbitTemplate;
    @Value("${rabbitmq.exchange}")
    private String exchange;
    @Value("${rabbitmq.routingkey}")
    private String routingKey;
    @Value("${rabbitmq.routingkey1}")
    private String routingKey1;
    @Value("${rabbitmq.routingkey2}")
    private String routingKey2;
    @Value("${rabbitmq.routingkey3}")
    private String routingKey3;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsr() {
        List<User> userList = (List<User>) rabbitTemplate.convertSendAndReceive(exchange, routingKey1, new User());
        final List<UserDto> userDtoList = userList.stream().map(userMapper::entityToDto).collect(Collectors.toList());
        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") Long id) {
        User user = (User) rabbitTemplate.convertSendAndReceive(exchange, routingKey2, id);
        return new ResponseEntity<>(userMapper.entityToDto(user), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody UserDto userDto) {
        User result = (User) rabbitTemplate.convertSendAndReceive(exchange, routingKey, userMapper.dtoToEntity(userDto));
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "{id}")
    public void deleteDoctor(@PathVariable("id") Long id) {
        rabbitTemplate.convertSendAndReceive(exchange, routingKey3, id);
    }

}
