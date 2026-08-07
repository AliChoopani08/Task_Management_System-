package com.Ali_Choopani.Task_Managment_System.service;

import com.Ali_Choopani.Task_Managment_System.dto.RefreshTokenSummary;
import com.Ali_Choopani.Task_Managment_System.entities.Device;
import com.Ali_Choopani.Task_Managment_System.entities.RefreshToken;
import com.Ali_Choopani.Task_Managment_System.mappers.RefreshTokenMapper;
import com.Ali_Choopani.Task_Managment_System.repositories.DeviceRepository;
import com.Ali_Choopani.Task_Managment_System.repositories.RefreshTokenRepository;
import com.Ali_Choopani.Task_Managment_System.services.user.device.refreshToken.RefreshTokenServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.UUID;

import static com.Ali_Choopani.Task_Managment_System.TestMocksHelper.MockWhenHelper.whenHelper;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
public class RefreshTokenServiceTest {

    @Mock
    private DeviceRepository deviceRepository;
    @Mock
    private RefreshTokenRepository repository;
    @Mock
    private RefreshTokenMapper mapper;

    @InjectMocks
    private RefreshTokenServiceImpl service;

    private Device device;
    private RefreshToken refreshToken;
    private RefreshTokenSummary tokenSummary;

    @BeforeEach
    void setUp() {
        device = Device.builder()
                .id(1L)
                .deviceUuid(randomUUID())
                .available(true)
                .build();
        refreshToken = RefreshToken.builder()
                .id(2L)
                .token(randomUUID())
                .revoked(false)
                .build();
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
}
