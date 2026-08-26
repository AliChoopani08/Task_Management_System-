package com.Ali_Choopani.Task_Management_System.services.user;

import com.Ali_Choopani.Task_Management_System.dto.user.AuthResponse;
import com.Ali_Choopani.Task_Management_System.dto.user.LoginRequest;
import com.Ali_Choopani.Task_Management_System.dto.user.UserViewSummary;
import com.Ali_Choopani.Task_Management_System.dto.user.device.refreshToken.RefreshTokenSummary;
import com.Ali_Choopani.Task_Management_System.dto.user.device.refreshToken.RegisterRequest;
import com.Ali_Choopani.Task_Management_System.dto.user.UserSummary;
import com.Ali_Choopani.Task_Management_System.dto.user.device.DeviceSummary;
import com.Ali_Choopani.Task_Management_System.entities.Profile;
import com.Ali_Choopani.Task_Management_System.entities.User;
import com.Ali_Choopani.Task_Management_System.exceptions.user.DuplicateUsername;
import com.Ali_Choopani.Task_Management_System.mappers.UserMapper;
import com.Ali_Choopani.Task_Management_System.repositories.UserRepository;
import com.Ali_Choopani.Task_Management_System.security.JwtService;
import com.Ali_Choopani.Task_Management_System.security.UserDetailImpl;
import com.Ali_Choopani.Task_Management_System.services.user.device.DeviceService;
import com.Ali_Choopani.Task_Management_System.services.user.device.refreshToken.RefreshTokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.Ali_Choopani.Task_Management_System.entities.UserRole.ROLE_USER;
import static com.Ali_Choopani.Task_Management_System.services.user.UserSpecification.searchByNameOrEmail;
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
        entity.setRole(ROLE_USER);
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

    @Override
    public Page<UserViewSummary> searchUsersByFullNameOrEmail(String fullNameOrEmail, Pageable pageable) {
        return repository.findAll(searchByNameOrEmail(fullNameOrEmail), pageable)
                .map(mapper::toViewSummary);
    }

    private void validateUniqueEmailAndPhone(String email, String phoneNumber) {
        boolean foundByEmail = false;
        boolean foundByPhoneNumber = false;

        if ((email != null && !email.isBlank())) {
             foundByEmail = repository.findByEmail(email)
                     .isPresent();}
        if ((phoneNumber != null && !phoneNumber.isBlank())) {
             foundByPhoneNumber = repository.findByPhoneNumber(phoneNumber)
                     .isPresent();}
        log.info("email     {} +                     found   {}", email, foundByEmail );
        log.info("phone     {} +                     found   {}", phoneNumber, foundByPhoneNumber );

        if (foundByEmail || foundByPhoneNumber) {
            final DuplicateUsername ex = new DuplicateUsername();
            log.warn(ex.getMessage());
            throw ex;
        }
    }
}
