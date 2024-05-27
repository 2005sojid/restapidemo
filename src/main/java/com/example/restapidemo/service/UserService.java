package com.example.restapidemo.service;

import com.example.restapidemo.entity.User;
import com.example.restapidemo.repository.UserRepo;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class UserService {

    private final UserRepo repo;



    public List<User> findAll() {
        return repo.findAll();
    }

    public User save(User user) {
        return repo.save(user);
    }

    public User findByUsername(String username) {
        return repo.findByUsername(username);
    }
}
