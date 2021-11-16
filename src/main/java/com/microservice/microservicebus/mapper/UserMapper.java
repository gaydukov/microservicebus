package com.microservice.microservicebus.mapper;

import com.microservice.microservicebus.dto.UserDto;
import com.microservice.microservicebus.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDto entityToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .secondName(user.getSecondName())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

    public User dtoToEntity(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .firstName(userDto.getFirstName())
                .secondName(userDto.getSecondName())
                .phoneNumber(userDto.getPhoneNumber())
                .build();
    }
}
