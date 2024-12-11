package com.captcha.simplecaptcha.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "DELIVERED_CAPTCHA")
public class DeliveredCaptcha {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(columnDefinition = "CHAR(32)", nullable = false)
    private String id;

    @Column(name = "TEXT_CAPTCHA", nullable = false)
    private String textCaptcha;

    @Column(name = "EXPIRED_AT", nullable = false)
    private LocalDateTime expiredAt;
}
