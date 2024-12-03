package org.example.controller;

import org.example.dto.CreateUserRequest;
import org.example.dto.CreateUserResponse;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpResponse;

@RestController
@RequestMapping("api")
public class UserController {

    @Autowired
    private UserService userService;
//http:localhost:8080/api/register
    @PostMapping("/register")
    public ResponseEntity <?> createUser(@RequestBody CreateUserRequest createUserRequest) {
        try {
            return new ResponseEntity<>(userService.createUser(createUserRequest), HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
