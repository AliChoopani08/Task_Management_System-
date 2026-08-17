package com.Ali_Choopani.Task_Management_System.repository;

import com.Ali_Choopani.Task_Management_System.entities.Device;
import com.Ali_Choopani.Task_Management_System.entities.RefreshToken;
import com.Ali_Choopani.Task_Management_System.entities.User;
import com.Ali_Choopani.Task_Management_System.repositories.DeviceRepository;
import com.Ali_Choopani.Task_Management_System.repositories.RefreshTokenRepository;
import com.Ali_Choopani.Task_Management_System.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static com.Ali_Choopani.Task_Management_System.entities.UserRole.ROLE_USER;
import static java.time.Duration.ofDays;
import static java.time.Instant.now;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class RefreshTokenRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private RefreshTokenRepository repository;
    private Device savedDevice;
    private RefreshToken refreshToken;
    private User user;

    @BeforeEach
    void setUp() {
        User user = User.builder()
                .phoneNumber("09876543210")
                .password("Akbar@1234")
                .role(ROLE_USER)
                .build();
        Device device = Device.builder()
                .deviceUuid(randomUUID())
                .build();
        device.addDeviceToUser(user);

        refreshToken = RefreshToken.builder()
                .token(randomUUID())
                .expiresAt(now().plus(ofDays(30)))
                .revoked(false)
                .build();
        refreshToken.addRefreshTokenToDevice(device);

        this.user = userRepository.save(user);
        savedDevice = deviceRepository.save(device);
    }

    @Test
    void shouldFindTheValidRefreshToken_withDeviceIdAndExpiration() {
        final Optional<RefreshToken> theValidToken = repository.findTheValidToken(savedDevice.getId(), now());

        assertThat(theValidToken.isPresent()).isTrue();
        theValidToken.ifPresent(t -> assertThat(t.getExpiresAt()).isAfter(now()));
    }

    @Test
    void shouldFindByTokenAndDeviceUuid_whenExists() {
        final UUID deviceUuid = savedDevice.getDeviceUuid();
        final UUID token = refreshToken.getToken();

        final Optional<RefreshToken> foundEntity = repository.findByTokenAndDevice_DeviceUuid(token, deviceUuid);

        assertThat(foundEntity.isPresent()).isTrue();
        foundEntity.ifPresent(r -> assertThat(r)
                .extracting(RefreshToken::getToken, rf -> rf.getDevice().getDeviceUuid(),
                        rf -> rf.getDevice().getUser().getPhoneNumber())
                .containsExactly(token, deviceUuid, "09876543210"));
    }

}
