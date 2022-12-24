package com.austin.walletapp.repositories;

import com.austin.walletapp.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

//    Optional<User> findByEmail(String username);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
