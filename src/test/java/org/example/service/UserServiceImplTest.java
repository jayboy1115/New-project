package org.example.service;

import org.example.dto.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;


    @Test
    void testThatInvalidEmailFormatIsRejected() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail("johnson-email");
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
        createUserRequest.setUserName("ibitoye");
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

    @Test
    void testThatUserCanLoginWithCorrectCredentials() {
        LoginRequest request = new LoginRequest();
        request.setEmail("john@example.com");
        request.setPassword("johnson");
        LoginResponse response = userService.login(request);
        response.setMessage("Login successful");
        assertNotNull(response);

    }
//    @Test
//    void testThatUserCanSearchForRecipe() {
//        CreateUserRequest createUserRequest = new CreateUserRequest();
//        createUserRequest.setUserName("johnson");
//        createUserRequest.setEmail("johnson@example.com");
//        createUserRequest.setPassword("johnson");
//        userService.createUser(createUserRequest);
//
//        List<CreateRecipeRequest> recipeRequests = new ArrayList<>();
//        recipeRequests.add(new CreateRecipeRequest("Rice", new ArrayList<>(List.of("water", "salt", "pepper")), "Breakfast"));
//        recipeRequests.add(new CreateRecipeRequest("ToastBread", new ArrayList<>(List.of("Flour", "Eggs", "Butter")), "Breakfast"));
//        recipeRequests.add(new CreateRecipeRequest("Spaghetti", new ArrayList<>(List.of("Pasta", "Tomato Sauce", "Cheese")), "Dinner"));
//
//        CreateRecipeResponse recipeResponse = userService.findRecipe(recipeRequests);
//        assertThat(recipeResponse).isNotNull();
//
//    }
}