package org.example.data.repository;

import org.example.data.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    boolean existsByEmail(String email);
    boolean existsByUserName(String username);

    User findByUserName(String userName);
}
