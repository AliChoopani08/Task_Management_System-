package com.Ali_Choopani.Task_Management_System.security;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.Ali_Choopani.Task_Management_System.entities.ProjectRole.ROLE_DEVELOPER;
import static com.Ali_Choopani.Task_Management_System.entities.ProjectRole.ROLE_MANAGER;
import static org.assertj.core.api.Assertions.assertThat;

public class JwtServiceTest {

    @Autowired
    private JwtService service;

    @BeforeEach
    void setUp() {
        service = new JwtServiceImpl();
    }

    @Test
    void shouldGenerateToken_whenSecretKeyIsAvailable() {
        final String generatedToken = service.generateToken(1L, ROLE_MANAGER.name());

        assertThat(generatedToken).isInstanceOf(String.class);
        assertThat(generatedToken).isNotEmpty();
    }

    @Test
    void shouldExtractUserId_whenTokenDecoded() {
        final long userId = 5L;
        final String generatedToken = service.generateToken(userId, ROLE_DEVELOPER.name());

        final Long foundId = service.extractId(generatedToken);

        assertThat(foundId).isEqualTo(userId);
    }

    @Test
    void shouldVerifyTheTokenExpiration_whenTokenDecoded() {
        final String generatedToken = service.generateToken(3L, ROLE_MANAGER.name());

        final boolean isTokenExpires = service.isTokenExpired(generatedToken);

        assertThat(isTokenExpires).isFalse();
    }
}
