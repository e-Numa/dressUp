package com.enuma.dressUp.service.serviceImpl;

import com.enuma.dressUp.entity.User;
import com.enuma.dressUp.repository.UserRepository;
import com.enuma.dressUp.service.UserServices;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServicesImpl implements UserServices {
    private final UserRepository userRepository;

    public UserServicesImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> loginUser(String email, String password) {
        // Find the user by email
        Optional<User> optionalUser = userRepository.findByEmail(email);
        // Validate the user's credentials
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (password.equals(user.getPassword())) {
                return optionalUser;
            }
        }
        return Optional.empty();
    }

    @Override
    public Long getUserIdByUsername(String username) {
        User user = userRepository.findByName(username);
        if (user != null) {
            return user.getUserId();
        }
        return null;
    }
}
