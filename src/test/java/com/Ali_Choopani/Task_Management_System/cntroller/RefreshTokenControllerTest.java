package com.Ali_Choopani.Task_Management_System.cntroller;

import com.Ali_Choopani.Task_Management_System.controllers.RefreshTokenController;
import com.Ali_Choopani.Task_Management_System.dto.user.device.refreshToken.RefreshAccessTokenRequest;
import com.Ali_Choopani.Task_Management_System.dto.user.device.refreshToken.RefreshAccessTokenResponse;
import com.Ali_Choopani.Task_Management_System.security.securityExceptionHandlers.AuthenticationEntryPoint;
import com.Ali_Choopani.Task_Management_System.security.CustomUserDetailsService;
import com.Ali_Choopani.Task_Management_System.security.JwtService;
import com.Ali_Choopani.Task_Management_System.security.UserDetailImpl;
import com.Ali_Choopani.Task_Management_System.services.user.device.refreshToken.RefreshTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static com.Ali_Choopani.Task_Management_System.entities.UserRole.ROLE_USER;
import static java.util.Collections.singleton;
import static java.util.UUID.randomUUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RefreshTokenController.class)
public class RefreshTokenControllerTest {

    @MockitoBean
    private RefreshTokenService service;
    @MockitoBean
    private JwtService jwtService;
    @MockitoBean
    private CustomUserDetailsService detailsService;
    @MockitoBean
    private AuthenticationEntryPoint entryPoint;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    private RefreshAccessTokenResponse accessTokenResponse;
    private UserDetailImpl userDetail;

    @BeforeEach
    void setUp() {
        accessTokenResponse = RefreshAccessTokenResponse.builder()
                .userId(1L)
                .deviceUuid(randomUUID())
                .refreshToken(randomUUID())
                .accessToken("generated.expected.access.token.1234@=")
                .build();
        userDetail = UserDetailImpl.builder()
                .id(2L)
                .username("AhmadRezaee345@gmail.com")
                .authorities(singleton(new SimpleGrantedAuthority(ROLE_USER.name())))
                .build();
    }

    @Test
    void shouldRefreshAccessTokenByDeviceUuidAndRefreshToken() throws Exception {
        RefreshAccessTokenRequest request = RefreshAccessTokenRequest.builder()
                .refreshToken(accessTokenResponse.refreshToken())
                .build() ;
        final Long userId = accessTokenResponse.userId();
        final UUID deviceUuid = accessTokenResponse.deviceUuid();
        final String expectedAccessToken = accessTokenResponse.accessToken();


        given(service.refreshAccessToken(any(UUID.class), any(RefreshAccessTokenRequest.class)))
                .willReturn(accessTokenResponse);

        mockMvc.perform(post("/token/access_token")
                .with(user(userDetail))
                .with(csrf())
                .header("X-Device-UUID", deviceUuid)
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.['user id']").value(userId))
                .andExpect(jsonPath("$.data.['access token']").value(expectedAccessToken));

    }
}
