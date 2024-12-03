package org.example.service;

import lombok.AllArgsConstructor;
import org.example.data.models.User;
import org.example.data.repository.UserRepository;
import org.example.dto.CreateUserRequest;
import org.example.dto.CreateUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public CreateUserResponse createUser(CreateUserRequest createUserRequest) {
        User newUser = userRepository.findByUserName(createUserRequest.getUserName());
        if (newUser != null) {
            throw new IllegalArgumentException("user already exist");
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

    private void validateRequest(CreateUserRequest createUserRequest) {
        String userName = createUserRequest.getUserName();
        String email = createUserRequest.getEmail();
        String password = createUserRequest.getPassword();

        if (createUserRequest.getUserName() == null || userName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            throw new IllegalArgumentException("field is required");
        }

        if (!isValidEmail(createUserRequest.getEmail()))throw new IllegalArgumentException("Invalid email format");

    }

    private boolean isValidEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }
}
