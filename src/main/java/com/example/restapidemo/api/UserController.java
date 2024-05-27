package com.example.restapidemo.api;

import com.example.restapidemo.entity.User;
import com.example.restapidemo.service.UserService;
import lombok.Data;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Data
@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;


    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.findAll();
    }



    @GetMapping("/user")
    public User getUser() {
        return (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }



}
