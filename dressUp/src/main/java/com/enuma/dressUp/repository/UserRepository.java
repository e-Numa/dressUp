package com.enuma.dressUp.repository;

import com.enuma.dressUp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUserNameOrEmail(String username, String email);

    User findByName(String username);

    Boolean existsByUserName(String username);

    Boolean existsByEmail(String email);



}
