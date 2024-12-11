package com.captcha.simplecaptcha.exception;

public class CaptchaGenerationException extends RuntimeException {

    public CaptchaGenerationException(String message) {
        super(message);
    }

    public CaptchaGenerationException(String message, Throwable th) {
        super(message, th);
    }
}
