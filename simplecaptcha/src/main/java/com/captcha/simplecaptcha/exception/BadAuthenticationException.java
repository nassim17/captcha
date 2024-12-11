package com.captcha.simplecaptcha.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class BadAuthenticationException extends RuntimeException {

    @Getter private final HttpStatus httpStatus;

    @Getter private final String httpCause;

    public BadAuthenticationException(BadAuthenticationErrorEnum errorEnum) {
        super(errorEnum.getMessage());
        this.httpStatus = errorEnum.httpStatus;
        this.httpCause = errorEnum.name();
    }

    public BadAuthenticationException(BadAuthenticationErrorEnum errorEnum, Exception e) {
        super(errorEnum.getMessage(), e);
        this.httpStatus = errorEnum.httpStatus;
        this.httpCause = errorEnum.name();
    }

    @Getter
    @AllArgsConstructor
    public enum BadAuthenticationErrorEnum {
        SECURITY_INVALID_CAPTCHA(HttpStatus.UNAUTHORIZED, "Captcha incorrect"),
        SECURITY_EXPIRED_CAPTCHA(HttpStatus.UNAUTHORIZED, "Captcha expiré"),
        SECURITY_BAD_CREDENTIALS(HttpStatus.UNAUTHORIZED, "Les informations saisies n’ont pas permis de se connecter"),
        SECURITY_LOCKED_ACCOUNT(HttpStatus.UNAUTHORIZED, "Compte verrouillé"),
        SECURITY_INACTIVE_ACCOUNT(HttpStatus.UNAUTHORIZED, "Compte désactivé"),
        SECURITY_EXPIRED_PASSWORD_ACCOUNT(HttpStatus.UNAUTHORIZED, "Compte avec mot de passe expiré"),
        SECURITY_TEMPLOCKED_ACCOUNT(HttpStatus.UNAUTHORIZED, "Compte bloqué"),
        SECURITY_BAD_AUTHORIZATIONS(HttpStatus.UNAUTHORIZED, "Compte non autorisé"),
        SECURITY_UNKNOWN_ACCOUNT_ROLE(HttpStatus.UNAUTHORIZED, "Aucun rôle connu de l'application pour cet utilisateur"),
        SECURITY_UNKNOWN_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "Refresh token inconnu"),
        SECURITY_EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "Refresh token expiré");

        private HttpStatus httpStatus;

        private String message;
    }
}
