package com.captcha.simplecaptcha.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.captcha.simplecaptcha.domain.DeliveredCaptcha;

public interface DeliveredCaptchaRepository extends JpaRepository<DeliveredCaptcha, String> {

    @Modifying
    int deleteByExpiredAtBefore(LocalDateTime now);
}
