package com.example.restapidemo.auth;

import com.example.restapidemo.config.JwtService;
import com.example.restapidemo.entity.Role;
import com.example.restapidemo.entity.Status;
import com.example.restapidemo.entity.User;
import com.example.restapidemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder().username(request.getUsername()).password(passwordEncoder.encode(request.getPassword())).role(Role.USER).createdAt(LocalDateTime.now()).email(request.getEmail()).status(Status.ACTIVE).build();
        userService.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>() , user);
        return AuthenticationResponse.builder().token(jwtToken).refreshToken(refreshToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        var user = userService.findByUsername(request.getUsername());
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
        return AuthenticationResponse.builder().token(jwtToken).refreshToken(refreshToken).build();
    }

    public AuthenticationResponse refresh(AuthenticationResponse response) {
        AuthenticationResponse resp = new AuthenticationResponse();
        String username = jwtService.extractUsername(response.getToken());
        User user = userService.findByUsername(username);
        if (jwtService.isTokenValid(response.getToken(), user)){
            var jwt = jwtService.generateToken(user);
            resp.setToken(jwt);
            resp.setRefreshToken(response.getToken());
        }
        return resp;
    }
}
