package com.Ali_Choopani.Task_Management_System.service;

import com.Ali_Choopani.Task_Management_System.dto.user.device.refreshToken.RefreshAccessTokenResponse;
import com.Ali_Choopani.Task_Management_System.dto.user.device.refreshToken.RefreshAccessTokenRequest;
import com.Ali_Choopani.Task_Management_System.dto.user.device.refreshToken.RefreshTokenSummary;
import com.Ali_Choopani.Task_Management_System.entities.Device;
import com.Ali_Choopani.Task_Management_System.entities.RefreshToken;
import com.Ali_Choopani.Task_Management_System.entities.User;
import com.Ali_Choopani.Task_Management_System.mappers.RefreshTokenMapper;
import com.Ali_Choopani.Task_Management_System.repositories.DeviceRepository;
import com.Ali_Choopani.Task_Management_System.repositories.RefreshTokenRepository;
import com.Ali_Choopani.Task_Management_System.security.JwtService;
import com.Ali_Choopani.Task_Management_System.services.user.device.refreshToken.RefreshTokenServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.UUID;

import static com.Ali_Choopani.Task_Management_System.TestMocksHelper.MockWhenHelper.whenHelper;
import static com.Ali_Choopani.Task_Management_System.entities.UserRole.ROLE_USER;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class RefreshTokenServiceTest {

    @Mock
    private DeviceRepository deviceRepository;
    @Mock
    private RefreshTokenRepository repository;
    @Mock
    private RefreshTokenMapper mapper;
    @Mock
    private JwtService jwtService;

    @InjectMocks
    private RefreshTokenServiceImpl service;

    private User user;
    private Device device;
    private RefreshToken refreshToken;
    private RefreshTokenSummary tokenSummary;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .phoneNumber("09876543210")
                .role(ROLE_USER)
                .build();
        device = Device.builder()
                .id(2L)
                .deviceUuid(randomUUID())
                .available(true)
                .build();
        device.addDeviceToUser(user);

        refreshToken = RefreshToken.builder()
                .id(3L)
                .token(randomUUID())
                .revoked(false)
                .build();
        refreshToken.addRefreshTokenToDevice(device);

        tokenSummary = new RefreshTokenSummary(refreshToken.getToken());

        refreshToken.addRefreshTokenToDevice(device);
    }

    @Test
    void shouldCreateNewRefreshToken_whenDeviceExistsAndTokenDoesNotExist() {
        final Long deviceId = device.getId();
        final UUID exceptedToken = refreshToken.getToken();

        whenHelper(deviceRepository.findByIdAndBeAvailable(anyLong()), of(device));
        whenHelper(repository.findTheValidToken(anyLong(), any(Instant.class)), empty());
        whenHelper(repository.save(any(RefreshToken.class)), refreshToken);
        whenHelper(mapper.toSummary(any(RefreshToken.class)), tokenSummary);

        final RefreshTokenSummary response = service.createNewToken(deviceId);

        assertThat(response.token())
                .isEqualTo(exceptedToken);
    }

    @Test
    void shouldRefreshAccessTokenByRefreshTokenAndDeviceUuid_whenRefreshTokenExistsAndBeValid() {
        final UUID deviceUuid = device.getDeviceUuid();
        final RefreshAccessTokenRequest request = new RefreshAccessTokenRequest(refreshToken.getToken());
        final String fakeGeneratedAccessToken = "fake.access.token@12345=";
        final Long userId = user.getId();

        whenHelper(repository.findByTokenAndDevice_DeviceUuid(any(UUID.class), any(UUID.class)), of(refreshToken));
        whenHelper(jwtService.generateToken(anyLong(), anyString()), fakeGeneratedAccessToken);

        final RefreshAccessTokenResponse response = service.refreshAccessToken(deviceUuid, request);

        assertThat(response)
                .extracting(RefreshAccessTokenResponse::userId, RefreshAccessTokenResponse::deviceUuid, RefreshAccessTokenResponse::accessToken)
                .containsExactly(userId, deviceUuid, fakeGeneratedAccessToken);
    }
}
