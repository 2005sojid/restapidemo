package com.example.restapidemo.api;

import com.example.restapidemo.entity.User;
import com.example.restapidemo.service.UserService;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }


    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.save(user);
    }


}
