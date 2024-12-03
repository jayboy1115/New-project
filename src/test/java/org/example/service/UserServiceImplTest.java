package org.example.service;

import org.example.dto.CreateUserRequest;
import org.example.dto.CreateUserResponse;
import org.example.data.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepository userRepository;


    @Test
    void testThatInvalidEmailFormatIsRejected() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail("invalid-email");
        createUserRequest.setUserName("invalid-name");
        createUserRequest.setPassword("invalid-password");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(createUserRequest);
        });
        assertEquals("Invalid email format", exception.getMessage());
    }

    @Test
    void testThatMissingRequiredFieldsAreHandled() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUserName(null);
        createUserRequest.setEmail(null);
        createUserRequest.setPassword(null);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(createUserRequest);
        });
        exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(createUserRequest);
        });
        exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(createUserRequest);
        });
    }

    @Test
    void testThatDuplicateEmailIsRejected() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail("john@example.com");
        createUserRequest.setUserName("badafeez");
        createUserRequest.setPassword("johnson");
        userService.createUser(createUserRequest);

        assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(createUserRequest);
        });
    }


    @Test
    void testThatDuplicateUsernameIsRejected() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUserName("johnson");
        userService.createUser(createUserRequest);
       assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(createUserRequest);
        });
    }
}