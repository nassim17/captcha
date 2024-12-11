package com.captcha.simplecaptcha.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.captcha.simplecaptcha.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
