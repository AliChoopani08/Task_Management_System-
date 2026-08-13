package com.Ali_Choopani.Task_Managment_System.integrationTest;

import com.Ali_Choopani.Task_Managment_System.dto.user.device.refreshToken.RefreshAccessTokenRequest;
import com.Ali_Choopani.Task_Managment_System.dto.user.device.refreshToken.RegisterRequest;
import com.Ali_Choopani.Task_Managment_System.entities.Device;
import com.Ali_Choopani.Task_Managment_System.entities.RefreshToken;
import com.Ali_Choopani.Task_Managment_System.entities.User;
import com.Ali_Choopani.Task_Managment_System.entities.UserRole;
import com.Ali_Choopani.Task_Managment_System.repositories.UserRepository;
import com.Ali_Choopani.Task_Managment_System.security.JwtService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import static com.Ali_Choopani.Task_Managment_System.entities.UserRole.ROLE_USER;
import static java.time.Duration.ofDays;
import static java.time.Instant.now;
import static java.util.UUID.randomUUID;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class RefreshTokenIntegrationTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private MockMvc mockMvc;

    private User savedUser;
    private Device device;
    private RefreshToken refreshToken;
    private String accessToken;


    @BeforeEach
    void setUp() throws Exception {
        User user = User.builder()
                .phoneNumber("09876543210")
                .password("Ahmad@ch123")
                .role(ROLE_USER)
                .build();
        device = Device.builder()
                .deviceUuid(randomUUID())
                .available(true)
                .build();
        device.addDeviceToUser(user);

        refreshToken = RefreshToken.builder()
                .token(randomUUID())
                .revoked(false)
                .expiresAt(now().plus(ofDays(30)))
                .build();
        refreshToken.addRefreshTokenToDevice(device);

         savedUser = userRepository.save(user);
    }

    @Test
    void shouldRefreshAccessTokenByDeviceUuidAndRefreshToken_whenUserHasLoggedInAndHaveValidDeviceAndRefreshToken() throws Exception {
        RefreshAccessTokenRequest request = RefreshAccessTokenRequest.builder()
                .refreshToken(refreshToken.getToken())
                .build();

        mockMvc.perform(post("/token/access_token")
                        .header("X-Device-UUID", device.getDeviceUuid())
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.['user id']").value(savedUser.getId()))
                .andExpect(jsonPath("$.data.['access token']").isNotEmpty());
    }
}
