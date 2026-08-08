package com.Ali_Choopani.Task_Managment_System.services.user;

import com.Ali_Choopani.Task_Managment_System.dto.user.AuthResponse;
import com.Ali_Choopani.Task_Managment_System.dto.user.LoginRequest;
import com.Ali_Choopani.Task_Managment_System.dto.user.device.refreshToken.RefreshTokenSummary;
import com.Ali_Choopani.Task_Managment_System.dto.user.device.refreshToken.RegisterRequest;
import com.Ali_Choopani.Task_Managment_System.dto.user.UserSummary;
import com.Ali_Choopani.Task_Managment_System.dto.user.device.DeviceSummary;
import com.Ali_Choopani.Task_Managment_System.entities.Profile;
import com.Ali_Choopani.Task_Managment_System.entities.User;
import com.Ali_Choopani.Task_Managment_System.exceptions.DuplicateUsername;
import com.Ali_Choopani.Task_Managment_System.mappers.UserMapper;
import com.Ali_Choopani.Task_Managment_System.repositories.UserRepository;
import com.Ali_Choopani.Task_Managment_System.security.JwtService;
import com.Ali_Choopani.Task_Managment_System.security.UserDetailImpl;
import com.Ali_Choopani.Task_Managment_System.services.user.device.DeviceService;
import com.Ali_Choopani.Task_Managment_System.services.user.device.refreshToken.RefreshTokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static java.time.Instant.now;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    private final DeviceService deviceService;
    private final RefreshTokenService refreshTokenService;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request, String userAgent) {
        validateUniqueEmailAndPhone(request.getEmail(), request.getPhoneNumber());

        final User entity = mapper.toEntity(request);

        entity.setPassword(encoder.encode(entity.getPassword()));
        Profile profile = Profile.builder().build();
        profile.addProfileToUser(entity);
        final User savedUser = repository.save(entity);

        final DeviceSummary deviceSummary = deviceService.createNewDevice(entity, userAgent);
        final RefreshTokenSummary refreshTokenSummary = refreshTokenService.createNewToken(deviceSummary.id());

        final String accessToken = jwtService.generateToken(entity.getId(), entity.getRole().name());
        final UserSummary userSummary = mapper.toSummary(savedUser);

        return new AuthResponse(userSummary, deviceSummary, refreshTokenSummary, accessToken);
    }

    @Override
    public AuthResponse login(LoginRequest request, UUID deviceUuid, String userAgent) {
        final Authentication authenticated = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmailOrPhoneNumber(), request.getPassword()));

        final UserDetailImpl principal = (UserDetailImpl) authenticated.getPrincipal();
        final User entity = mapper.toEntity(principal);
        final DeviceSummary device = deviceService.findOrCreateDevice(entity, deviceUuid, userAgent);
        final RefreshTokenSummary refreshToken = refreshTokenService.createNewToken(device.id());
        final String jwtToken = jwtService.generateToken(entity.getId(), entity.getRole().name());

        return new AuthResponse(mapper.toSummary(entity), device, refreshToken, jwtToken);
    }

    private void validateUniqueEmailAndPhone(String email, String phoneNumber) {
        if (repository.findByEmail(email).isPresent() || repository.findByPhoneNumber(phoneNumber).isPresent()) {
            final DuplicateUsername ex = new DuplicateUsername();
            log.warn(ex.getMessage());
            throw ex;
        }
    }
}
