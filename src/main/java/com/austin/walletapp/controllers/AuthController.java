package com.austin.walletapp.controllers;

import com.austin.walletapp.dtos.UserDto;
import com.austin.walletapp.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("signup")
    public ResponseEntity<Object> createUser(@RequestBody UserDto userDto) {

        return new ResponseEntity<>("Account created", HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity<Object> loginUser(@RequestBody UserDto userDto) {

        return new ResponseEntity<>("Login successful", HttpStatus.OK);
    }
}
