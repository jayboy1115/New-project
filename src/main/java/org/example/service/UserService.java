package org.example.service;

import org.example.dto.CreateUserRequest;
import org.example.dto.CreateUserResponse;
import org.example.dto.LoginRequest;
import org.example.dto.LoginResponse;

public interface UserService {
    CreateUserResponse createUser(CreateUserRequest createUserRequest);

    LoginResponse login(LoginRequest request);
}
