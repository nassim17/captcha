package com.captcha.simplecaptcha.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.captcha.simplecaptcha.repository.UserRepository;
import com.captcha.simplecaptcha.model.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public boolean checkPassword(User user, String password) {
        return password.equals(user.getPassword());
    }
}
