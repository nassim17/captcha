package com.captcha.simplecaptcha.helper;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class HttpHelper {

    private HttpHelper(){}

    public static Cookie createCaptchaCookie(final String loginSession) {
        // Ajouter le cookie HTTP-only
        Cookie captchaCookie = new Cookie("loginSession", loginSession);
        captchaCookie.setHttpOnly(true); // Marque le cookie comme HTTP-only
        captchaCookie.setSecure(true); // Utiliser cette option si HTTPS est activé
        captchaCookie.setPath("/"); // Rendre le cookie accessible sur tout le site
        captchaCookie.setMaxAge(60); // Définir une durée de vie (en secondes), ici 1 minute

        return captchaCookie;
    }

    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        if (request.getCookies() == null) {
            return "";
        }

        for (Cookie cookie : request.getCookies()) {
            if (cookieName.equalsIgnoreCase(cookie.getName())) {
                return cookie.getValue();
            }
        }

        return "";
    }
}
