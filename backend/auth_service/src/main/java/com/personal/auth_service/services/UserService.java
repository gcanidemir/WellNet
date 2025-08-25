package com.personal.auth_service.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.personal.auth_service.models.User;
import com.personal.auth_service.repositories.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
        
    }

}
