package com.Ali_Choopani.Task_Managment_System.services.user.device.refreshToken;

import com.Ali_Choopani.Task_Managment_System.dto.RefreshTokenSummary;
import com.Ali_Choopani.Task_Managment_System.entities.Device;
import com.Ali_Choopani.Task_Managment_System.entities.RefreshToken;
import com.Ali_Choopani.Task_Managment_System.exceptions.DuplicateRefreshToken;
import com.Ali_Choopani.Task_Managment_System.exceptions.InvalidDevice;
import com.Ali_Choopani.Task_Managment_System.mappers.RefreshTokenMapper;
import com.Ali_Choopani.Task_Managment_System.repositories.DeviceRepository;
import com.Ali_Choopani.Task_Managment_System.repositories.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

import static java.time.Instant.now;
import static java.util.UUID.randomUUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService{

    private final DeviceRepository deviceRepository;
    private final RefreshTokenRepository repository;
    private final RefreshTokenMapper mapper;
    
    @Override
    @Transactional
    public RefreshTokenSummary createNewToken(Long deviceId) {
        final Device device = deviceRepository.findByIdAndBeAvailable(deviceId)
                .orElseThrow(() -> new InvalidDevice(deviceId));
        repository.findTheValidToken(device.getId(), now())
                .ifPresent(rf -> {
                    throw new DuplicateRefreshToken(rf.getDevice().getId() ,rf.getId());
                });
        final Duration expirationDuration = Duration.ofDays(30);

        RefreshToken token = RefreshToken.builder()
                .token(randomUUID())
                .expiresAt(now().plus(expirationDuration))
                .revoked(false)
                .build();
        token.addRefreshTokenToDevice(device);
        final RefreshToken savedToken = repository.save(token);

        return mapper.toSummary(savedToken);
    }
}
