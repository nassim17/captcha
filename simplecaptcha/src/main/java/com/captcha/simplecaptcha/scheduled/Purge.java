package com.captcha.simplecaptcha.scheduled;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.captcha.simplecaptcha.service.CaptchaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Component
public class Purge {

    private final CaptchaService captchaService;

    @Scheduled(fixedRateString = "${purge.expired-captcha.seconde}000")
    public void deleteExpiredCaptcha() {
        log.info("Début purge des captchas expirés");
        int nbCaptchaPurgees = captchaService.purgeExpiredCaptcha();
        log.info("Fin purge des captchas expirés, {} lignes supprimées", nbCaptchaPurgees);
    }
}
