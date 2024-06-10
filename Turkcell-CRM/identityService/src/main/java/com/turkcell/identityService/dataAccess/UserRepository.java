package com.turkcell.identityService.dataAccess;

import com.turkcell.identityService.entitites.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findUserByEmail(String username);

    Optional<User> findByEmailAndPassword(String email, String password);
}
