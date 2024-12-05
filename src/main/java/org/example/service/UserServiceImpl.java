package org.example.service;

import lombok.AllArgsConstructor;
import org.example.data.models.User;
import org.example.data.repository.UserRepository;
import org.example.dto.CreateUserRequest;
import org.example.dto.CreateUserResponse;
import org.example.dto.LoginRequest;
import org.example.dto.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public CreateUserResponse createUser(CreateUserRequest createUserRequest) {
        User existingUserByUserName = userRepository.findByUserName(createUserRequest.getUserName());
        if (existingUserByUserName != null) {
            throw new IllegalArgumentException("Username already exists");
        }

        User existingUserByEmail = userRepository.findByEmail(createUserRequest.getEmail());
        if (existingUserByEmail != null) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();
        validateRequest(createUserRequest);
        user.setUserName(createUserRequest.getUserName());
        user.setPassword(createUserRequest.getPassword());
        user.setEmail(createUserRequest.getEmail());
        userRepository.save(user);

        CreateUserResponse createUserResponse = new CreateUserResponse();
        createUserResponse.setMessage("Successfully created user");
        return createUserResponse;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        User foundUser = userRepository.findByEmail(request.getEmail());
        if (foundUser.getPassword().equalsIgnoreCase(request.getPassword())) {
            LoginResponse response = new LoginResponse();
            response.setMessage("Login successful");
            return response;
        }
        throw new IllegalArgumentException("Wrong password");
    }

    private void validateRequest(CreateUserRequest createUserRequest) {
        String userName = createUserRequest.getUserName();
        String email = createUserRequest.getEmail();
        String password = createUserRequest.getPassword();

        if (createUserRequest.getUserName() == null || userName.isEmpty() || email == null || email.isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("field is required");
        }

        if (!isValidEmail(createUserRequest.getEmail()))throw new IllegalArgumentException("Invalid email format");

    }

    private boolean isValidEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }
}
