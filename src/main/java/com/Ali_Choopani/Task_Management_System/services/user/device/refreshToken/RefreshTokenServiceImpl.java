package com.Ali_Choopani.Task_Management_System.services.user.device.refreshToken;

import com.Ali_Choopani.Task_Management_System.dto.user.device.refreshToken.RefreshAccessTokenResponse;
import com.Ali_Choopani.Task_Management_System.dto.user.device.refreshToken.RefreshAccessTokenRequest;
import com.Ali_Choopani.Task_Management_System.dto.user.device.refreshToken.RefreshTokenSummary;
import com.Ali_Choopani.Task_Management_System.entities.Device;
import com.Ali_Choopani.Task_Management_System.entities.RefreshToken;
import com.Ali_Choopani.Task_Management_System.entities.User;
import com.Ali_Choopani.Task_Management_System.exceptions.user.device.BlockedDeviceException;
import com.Ali_Choopani.Task_Management_System.exceptions.user.device.InvalidDeviceException;
import com.Ali_Choopani.Task_Management_System.exceptions.user.device.refreshToken.DuplicateRefreshTokenException;
import com.Ali_Choopani.Task_Management_System.exceptions.user.device.refreshToken.NotFoundRefreshTokenException;
import com.Ali_Choopani.Task_Management_System.exceptions.user.device.refreshToken.RevokedOrExpiredRefreshToken;
import com.Ali_Choopani.Task_Management_System.mappers.RefreshTokenMapper;
import com.Ali_Choopani.Task_Management_System.repositories.DeviceRepository;
import com.Ali_Choopani.Task_Management_System.repositories.RefreshTokenRepository;
import com.Ali_Choopani.Task_Management_System.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.UUID;

import static java.time.Instant.now;
import static java.util.UUID.randomUUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService{

    private final DeviceRepository deviceRepository;
    private final RefreshTokenRepository repository;
    private final RefreshTokenMapper mapper;
    private final JwtService jwtService;
    
    @Override
    @Transactional
    public RefreshTokenSummary createNewToken(Long deviceId) {
        final Device device = deviceRepository.findByIdAndBeAvailable(deviceId)
                .orElseThrow(() -> new InvalidDeviceException(deviceId));
        repository.findTheValidToken(device.getId(), now())
                .ifPresent(rf -> {
                    throw new DuplicateRefreshTokenException(rf.getDevice().getId() ,rf.getId());
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

    @Override
    @Transactional
    public RefreshAccessTokenResponse refreshAccessToken(UUID deviceUuid, RefreshAccessTokenRequest request) {
        final RefreshToken refreshToken = repository.findByTokenAndDevice_DeviceUuid(request.getRefreshToken(), deviceUuid)
                .orElseThrow(() -> new NotFoundRefreshTokenException(request.getRefreshToken()));
        final Device device = refreshToken.getDevice();
        final User user = device.getUser();

        if (!device.isAvailable())
            throw new BlockedDeviceException(device.getId());
        if (refreshToken.isRevoked() && refreshToken.getExpiresAt().isBefore(now()))
            throw new RevokedOrExpiredRefreshToken(refreshToken.getToken());

        final String generatedToken = jwtService.generateToken(user.getId(), user.getRole().name());

        return new RefreshAccessTokenResponse(user.getId(), device.getDeviceUuid(), refreshToken.getToken(), generatedToken);
    }
}
