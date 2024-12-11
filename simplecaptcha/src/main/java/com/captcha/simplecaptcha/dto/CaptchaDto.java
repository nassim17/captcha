package com.captcha.simplecaptcha.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CaptchaDto {

    private String textCaptcha;
    private String imageCaptcha;
    private String loginSessionId;
}
