package com.microservice.microservicebus.repository;

import com.microservice.microservicebus.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class RedisRepository {
    public static final String HASH_KEY = "User";
    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate template;

    public User save(User user) {
        user.setId((long) (template.opsForHash().values(HASH_KEY).size()+1));
        template.opsForHash().put(HASH_KEY, user.getId(), user);
        return user;
    }

    public List<User> findAll() {
        return template.opsForHash().values(HASH_KEY);
    }

    public User findById(Long id) {
        return (User) template.opsForHash().get(HASH_KEY, id);
    }


    public String deleteUser(Long id) {
        template.opsForHash().delete(HASH_KEY, id);
        return "product removed !!";
    }
}
