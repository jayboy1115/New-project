package org.example.service;

import org.example.dto.CreateUserRequest;
import org.example.dto.CreateUserResponse;

public interface UserService {
    CreateUserResponse createUser(CreateUserRequest createUserRequest);
}
