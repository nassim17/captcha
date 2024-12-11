package com.captcha.simplecaptcha.service;

import org.springframework.stereotype.Service;

import com.captcha.simplecaptcha.exception.BadAuthenticationException;
import com.captcha.simplecaptcha.model.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserService userService;

    public User authenticate(String username, String password) {
        return userService.findByUsername(username)
                .filter(user -> user.getPassword().equals(password))
                .orElseThrow(() -> new BadAuthenticationException(BadAuthenticationException.BadAuthenticationErrorEnum.SECURITY_BAD_CREDENTIALS));
    }
}
