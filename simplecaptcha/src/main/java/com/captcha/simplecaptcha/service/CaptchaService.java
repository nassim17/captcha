package com.captcha.simplecaptcha.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.captcha.simplecaptcha.domain.DeliveredCaptcha;
import com.captcha.simplecaptcha.dto.CaptchaDto;
import com.captcha.simplecaptcha.exception.BadAuthenticationException;
import com.captcha.simplecaptcha.helper.CaptchaHelper;
import com.captcha.simplecaptcha.repository.DeliveredCaptchaRepository;

import cn.apiclub.captcha.Captcha;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CaptchaService {

    private final DeliveredCaptchaRepository deliveredCaptchaRepository;

    private final CaptchaHelper captchaHelper;

    @Value("${captcha.duration.seconde}")
    private int captchaDurationSeconde;

    @Transactional
    public int purgeExpiredCaptcha() {
        return deliveredCaptchaRepository.deleteByExpiredAtBefore(LocalDateTime.now());
    }

    @Transactional
    public CaptchaDto generateCaptcha() {
        CaptchaDto captchaDTO = new CaptchaDto();
        Captcha captcha = captchaHelper.createCaptcha();
        captchaDTO.setTextCaptcha(captcha.getAnswer());
        captchaDTO.setImageCaptcha(captchaHelper.encodeCaptcha(captcha));
        DeliveredCaptcha deliveredCaptcha = new DeliveredCaptcha();
        deliveredCaptcha.setTextCaptcha(captchaDTO.getTextCaptcha());
        deliveredCaptcha.setExpiredAt(LocalDateTime.now().plusSeconds(captchaDurationSeconde));
        deliveredCaptchaRepository.save(deliveredCaptcha);
        captchaDTO.setLoginSessionId(deliveredCaptcha.getId());
        return captchaDTO;
    }

    @Transactional
    public void validate(String inputCaptcha, String loginSessionId) {

        // Récupération du captcha correspondant en BDD
        Optional<DeliveredCaptcha> optionalDeliveredCaptcha = deliveredCaptchaRepository.findById(loginSessionId);
        if (optionalDeliveredCaptcha.isEmpty()) {
            throw new BadAuthenticationException(BadAuthenticationException.BadAuthenticationErrorEnum.SECURITY_EXPIRED_CAPTCHA);
        }

        DeliveredCaptcha deliveredCaptcha = optionalDeliveredCaptcha.get();
        if (deliveredCaptcha.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new BadAuthenticationException(BadAuthenticationException.BadAuthenticationErrorEnum.SECURITY_EXPIRED_CAPTCHA);
        }

        String textCaptcha = deliveredCaptcha.getTextCaptcha();

        // Contrôle du texte du captcha
        if (!textCaptcha.equals(inputCaptcha)) {
            throw new BadAuthenticationException(BadAuthenticationException.BadAuthenticationErrorEnum.SECURITY_INVALID_CAPTCHA);
        }

        // Si on en est là, c'est qu'il est valide. Nettoyage
        deliveredCaptchaRepository.delete(deliveredCaptcha);
    }
}
