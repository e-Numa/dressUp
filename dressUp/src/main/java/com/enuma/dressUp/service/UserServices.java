package com.enuma.dressUp.service;

import com.enuma.dressUp.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserServices {
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    User updateUser(User user);
    Optional<User> loginUser(String email, String password);
    void deleteUser(Long id);
    Long getUserIdByUsername(String name);
}
