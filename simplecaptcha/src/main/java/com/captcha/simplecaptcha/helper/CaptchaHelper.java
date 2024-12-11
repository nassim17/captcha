package com.captcha.simplecaptcha.helper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.captcha.simplecaptcha.exception.CaptchaGenerationException;

import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.backgrounds.SquigglesBackgroundProducer;
import cn.apiclub.captcha.noise.CurvedLineNoiseProducer;
import cn.apiclub.captcha.text.producer.DefaultTextProducer;
import cn.apiclub.captcha.text.renderer.DefaultWordRenderer;

@Component
public class CaptchaHelper {

    @Value("${captcha.width}")
    private Integer width;

    @Value("${captcha.height}")
    private Integer height;

    public Captcha createCaptcha() {
        return new Captcha.Builder(width, height)
                .addBackground(new SquigglesBackgroundProducer())
                .addText(new DefaultTextProducer(), new DefaultWordRenderer())
                .addNoise(new CurvedLineNoiseProducer())
                .build();
    }

    public String encodeCaptcha(Captcha captcha) {
        String image = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(captcha.getImage(), "jpg", bos);
            byte[] byteArray = Base64.getEncoder().encode(bos.toByteArray());
            image = new String(byteArray);
        } catch (IOException e) {
            throw new CaptchaGenerationException("Error during captcha image generation", e);
        }
        return image;
    }
}
