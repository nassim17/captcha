package com.captcha.simplecaptcha.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.captcha.simplecaptcha.dto.CaptchaDto;
import com.captcha.simplecaptcha.service.CaptchaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/oauth")
public class CaptchaController {

    private final CaptchaService captchaService;

    @GetMapping("/captcha")
    public ResponseEntity<String> computeCaptcha() {
        CaptchaDto captchaDTO = captchaService.generateCaptcha();

        HttpHeaders headers = new HttpHeaders();
        String cookieHeaderValue =
                String.format("loginSession=%s; Path=/; HttpOnly", captchaDTO.getLoginSessionId());
        headers.add("Set-Cookie", cookieHeaderValue);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(captchaDTO.getImageCaptcha());
    }
}
