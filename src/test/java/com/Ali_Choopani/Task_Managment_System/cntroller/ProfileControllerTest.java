package com.Ali_Choopani.Task_Managment_System.cntroller;

import com.Ali_Choopani.Task_Managment_System.controllers.ProfileController;
import com.Ali_Choopani.Task_Managment_System.dto.user.profile.CompleteOrUpdateProfileRequest;
import com.Ali_Choopani.Task_Managment_System.dto.user.profile.ProfileSummary;
import com.Ali_Choopani.Task_Managment_System.entities.UserRole;
import com.Ali_Choopani.Task_Managment_System.security.CustomUserDetailsService;
import com.Ali_Choopani.Task_Managment_System.security.JwtAuthFilter;
import com.Ali_Choopani.Task_Managment_System.security.JwtService;
import com.Ali_Choopani.Task_Managment_System.security.UserDetailImpl;
import com.Ali_Choopani.Task_Managment_System.services.user.profile.ProfileService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import java.time.LocalDate;
import java.util.Collections;

import static com.Ali_Choopani.Task_Managment_System.entities.UserRole.ROLE_USER;
import static java.time.LocalDate.of;
import static java.util.Collections.singleton;
import static org.awaitility.Awaitility.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProfileController.class)
public class ProfileControllerTest {

    @MockitoBean
    private ProfileService service;
    @MockitoBean
    private JwtService jwtService;
    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    private ProfileSummary profileSummary;
    private UserDetailImpl fakeLoggedInUser;

    @BeforeEach
    void setUp() {

        fakeLoggedInUser = UserDetailImpl.builder()
                .id(2L)
                .username("Akbar_Ahmadi123@gmail.com")
                .authorities(singleton(new SimpleGrantedAuthority(ROLE_USER.name())))
                .build();
    }

    @Test
    void shouldCompleteProfileFields_whereUserExists() throws Exception {
        profileSummary = ProfileSummary.builder()
                .id(1L)
                .firstName("Reza")
                .surname("Ahmadi")
                .age("23 years, 3 months, 12 days")
                .biography("Site Developer")
                .userId(2L)
                .build();
        CompleteOrUpdateProfileRequest request = CompleteOrUpdateProfileRequest.builder()
                .firstName("Reza")
                .surname("Ahmadi")
                .birthDate(of(2003, 3, 12))
                .biography("Site Developer")
                .build();
        final Long userId = fakeLoggedInUser.getId();

        given(service.completeProfileFields(any(CompleteOrUpdateProfileRequest.class), anyLong()))
                .willReturn(profileSummary);

        mockMvc.perform(post(("/profile"))
                        .with(user(fakeLoggedInUser))
                        .with(csrf())
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.['first name']").value("Reza"))
                .andExpect(jsonPath("$.data.['user id']").value(userId));

        verify(service)
                .completeProfileFields(any(CompleteOrUpdateProfileRequest.class), anyLong());
    }
}
