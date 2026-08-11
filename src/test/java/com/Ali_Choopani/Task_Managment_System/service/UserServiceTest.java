package com.Ali_Choopani.Task_Managment_System.service;

import com.Ali_Choopani.Task_Managment_System.dto.user.AuthResponse;
import com.Ali_Choopani.Task_Managment_System.dto.user.LoginRequest;
import com.Ali_Choopani.Task_Managment_System.dto.user.device.refreshToken.RefreshTokenSummary;
import com.Ali_Choopani.Task_Managment_System.dto.user.device.refreshToken.RegisterRequest;
import com.Ali_Choopani.Task_Managment_System.dto.user.UserSummary;
import com.Ali_Choopani.Task_Managment_System.dto.user.device.DeviceSummary;
import com.Ali_Choopani.Task_Managment_System.entities.User;
import com.Ali_Choopani.Task_Managment_System.mappers.UserMapper;
import com.Ali_Choopani.Task_Managment_System.repositories.UserRepository;
import com.Ali_Choopani.Task_Managment_System.security.JwtService;
import com.Ali_Choopani.Task_Managment_System.security.UserDetailImpl;
import com.Ali_Choopani.Task_Managment_System.services.user.device.DeviceService;
import com.Ali_Choopani.Task_Managment_System.services.user.device.refreshToken.RefreshTokenService;
import com.Ali_Choopani.Task_Managment_System.services.user.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static com.Ali_Choopani.Task_Managment_System.TestMocksHelper.MockWhenHelper.whenHelper;
import static com.Ali_Choopani.Task_Managment_System.entities.UserRole.ROLE_USER;
import static java.util.Collections.singleton;
import static java.util.List.of;
import static java.util.Optional.empty;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private static final String USER_AGENT = "User's device web information ";
    private final String JWT_TOKEN = "fake.jwt.token.1234";
    @Mock
    private UserRepository repository;
    @Mock
    private UserMapper mapper;
    @Mock
    private PasswordEncoder encoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authManager;
    @Mock
    private DeviceService deviceService;
    @Mock
    private RefreshTokenService refreshTokenService;

    @InjectMocks
    private UserServiceImpl service;


    private User user;
    private UserSummary userSummary;
    private DeviceSummary deviceSummary;
    private RefreshTokenSummary refreshTokenSummary;
    private UserDetailImpl userDetail;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(20L)
                .phoneNumber("09876543210")
                .password("Ali@12345")
                .role(ROLE_USER)
                .build();
        userSummary = UserSummary.builder()
                .id(20L)
                .phoneNumber("09876543210")
                .role(ROLE_USER)
                .build();
        userDetail = UserDetailImpl.builder()
                .id(user.getId())
                .username(user.getPhoneNumber())
                .authorities(singleton(new SimpleGrantedAuthority(user.getRole().name())))
                .build();
        deviceSummary = new DeviceSummary(22L, randomUUID());
        refreshTokenSummary = new RefreshTokenSummary(randomUUID());
    }

    @Test
    void shouldCreateNewUser_whenDoesNotExist() {
        RegisterRequest request = RegisterRequest.builder()
                .phoneNumber("098765432101")
                .password("Ali@12345")
                .build();

        whenHelper(mapper.toEntity(any(RegisterRequest.class)), user);
        whenHelper(deviceService.createNewDevice(any(User.class), anyString()), deviceSummary);
        whenHelper(encoder.encode(anyString()), user.getPassword());
        whenHelper(refreshTokenService.createNewToken(anyLong()), refreshTokenSummary);
        whenHelper(repository.save(any(User.class)), user);
        whenHelper(jwtService.generateToken(anyLong(), anyString()), JWT_TOKEN);
        whenHelper(mapper.toSummary(any(User.class)), userSummary);

        final AuthResponse response = service.register(request, USER_AGENT);

        assertThat(response)
                .extracting(r -> r.user().id(), r -> r.device().id())
                .containsExactly(20L, 22L);
    }

    @Test
    void shouldLogin_whenUsernameExists() {
        LoginRequest request = LoginRequest.builder()
                .emailOrPhoneNumber("09876543210")
                .password("Ali@12345")
                .build();
        Authentication exceptedAuth = new UsernamePasswordAuthenticationToken(userDetail,
                empty(), singleton(userDetail.getAuthorities().iterator().next()));

        whenHelper(authManager.authenticate(any(Authentication.class)), exceptedAuth);
        whenHelper(mapper.toEntity(any(UserDetailImpl.class)), user);
        whenHelper(deviceService.findOrCreateDevice(any(User.class), any(UUID.class), anyString()), deviceSummary);
        whenHelper(refreshTokenService.createNewToken(anyLong()), refreshTokenSummary);
        whenHelper(jwtService.generateToken(anyLong(), anyString()), JWT_TOKEN);
        whenHelper(mapper.toSummary(any(User.class)), userSummary);

        final AuthResponse response = service.login(request, deviceSummary.deviceUuid(), USER_AGENT);

        assertThat(response)
                .extracting(r -> r.user().id(), r -> r.device().id(), AuthResponse::accessToken)
                .containsExactly(20L, 22L, JWT_TOKEN);
    }

}
