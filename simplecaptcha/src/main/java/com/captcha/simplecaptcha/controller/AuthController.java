package com.captcha.simplecaptcha.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.captcha.simplecaptcha.dto.CaptchaDto;
import com.captcha.simplecaptcha.exception.BadAuthenticationException;
import com.captcha.simplecaptcha.helper.HttpHelper;
import com.captcha.simplecaptcha.service.AuthService;
import com.captcha.simplecaptcha.service.CaptchaService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class AuthController {

    private final AuthService authService;

    private final CaptchaService captchaService;

    @GetMapping("/login")
    public String showLoginPage(Model model, HttpServletResponse response) {
        CaptchaDto captcha = captchaService.generateCaptcha();

        model.addAttribute("captchaImage", "data:image/png;base64," + captcha.getImageCaptcha());

        response.addCookie(HttpHelper.createCaptchaCookie(captcha.getLoginSessionId()));

        return "login";
    }

    @PostMapping("/login")
    public String authenticate(@RequestParam String username, @RequestParam String password,
            @RequestParam String captchaSolution, Model model, HttpServletRequest request, HttpServletResponse response) {

        try {
            // Récupération du sessionId
            String loginSessionId = HttpHelper.getCookieValue(request, "loginSession");

            // Vérification du captcha
            captchaService.validate(captchaSolution, loginSessionId);

            // Vérification de l'utilisateur et du mot de passe
            authService.authenticate(username, password);

            return "redirect:/home";

        } catch (BadAuthenticationException e) {
            model.addAttribute("loginError", e.getMessage());
        }

        CaptchaDto captcha = captchaService.generateCaptcha();

        model.addAttribute("captchaImage", "data:image/png;base64," + captcha.getImageCaptcha());

        response.addCookie(HttpHelper.createCaptchaCookie(captcha.getLoginSessionId()));

        return "login";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }
}
