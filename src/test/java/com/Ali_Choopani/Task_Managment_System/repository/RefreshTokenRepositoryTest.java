package com.Ali_Choopani.Task_Managment_System.repository;

import com.Ali_Choopani.Task_Managment_System.entities.Device;
import com.Ali_Choopani.Task_Managment_System.entities.RefreshToken;
import com.Ali_Choopani.Task_Managment_System.repositories.DeviceRepository;
import com.Ali_Choopani.Task_Managment_System.repositories.RefreshTokenRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static java.time.Duration.ofDays;
import static java.time.Instant.now;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class RefreshTokenRepositoryTest {

    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private RefreshTokenRepository repository;
    private Device savedDevice;

    @BeforeEach
    void setUp() {
        Device device = Device.builder()
                .deviceUuid(randomUUID())
                .build();
        RefreshToken token = RefreshToken.builder()
                .token(randomUUID())
                .expiresAt(now().plus(ofDays(30)))
                .revoked(false)
                .build();
        token.addRefreshTokenToDevice(device);

        savedDevice = deviceRepository.save(device);
    }

    @Test
    void shouldFindTheValidRefreshToken_withDeviceIdAndExpiration() {
        final Optional<RefreshToken> theValidToken = repository.findTheValidToken(savedDevice.getId(), now());

        assertThat(theValidToken.isPresent()).isTrue();
        theValidToken.ifPresent(t -> assertThat(t.getExpiresAt()).isAfter(now()));
    }
}
