package com.microservice.microservicebus.controller;

import com.microservice.microservicebus.dto.UserDto;
import com.microservice.microservicebus.entity.User;
import com.microservice.microservicebus.mapper.UserMapper;
import com.microservice.microservicebus.service.RedisService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/redis")
public class RedisController {
    private final RedisService redisService;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsr() {
        final List<UserDto> userDtoList = redisService.getAllUsers().stream().map(userMapper::entityToDto).collect(Collectors.toList());
        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userMapper.entityToDto(redisService.getUserById(id)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody UserDto userDto) {
        User result = redisService.saveUser(userMapper.dtoToEntity(userDto));
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "{id}")

    public void deleteDoctor(@PathVariable("id") Long id) {
        redisService.deleteUser(id);
    }
}
