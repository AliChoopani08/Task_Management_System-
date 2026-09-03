package com.Ali_Choopani.Task_Management_System.cntroller;

import com.Ali_Choopani.Task_Management_System.controllers.WorkLogController;
import com.Ali_Choopani.Task_Management_System.dto.comment.CreateWorkLogRequest;
import com.Ali_Choopani.Task_Management_System.dto.comment.WorkLogSummary;
import com.Ali_Choopani.Task_Management_System.security.CustomUserDetailsService;
import com.Ali_Choopani.Task_Management_System.security.JwtService;
import com.Ali_Choopani.Task_Management_System.security.UserDetailImpl;
import com.Ali_Choopani.Task_Management_System.security.securityExceptionHandlers.AuthenticationEntryPoint;
import com.Ali_Choopani.Task_Management_System.services.comment.WorkLogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static com.Ali_Choopani.Task_Management_System.entities.UserRole.ROLE_USER;
import static com.Ali_Choopani.Task_Management_System.testFactories.UserTestFactory.createUserDetail;
import static java.time.LocalDateTime.of;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WorkLogController.class)
public class WorkLogControllerTest {

    @MockitoBean
    private JwtService jwtService;
    @MockitoBean
    private CustomUserDetailsService detailsService;
    @MockitoBean
    private AuthenticationEntryPoint entryPoint;

    @MockitoBean
    private WorkLogService service;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    private UserDetailImpl currentUser;
    private WorkLogSummary summary;

    @BeforeEach
    void setUp() {
        currentUser = createUserDetail(1L, "Ahmad_1376.sajad@gmail.com", ROLE_USER);
        summary = WorkLogSummary.builder()
                .taskId(2L)
                .taskTitle("Implementation Login Flow")
                .description("Jwt authentication was implemented")
                .createdAt(of(2026, 7,20, 10, 30))
                .authorId(1L)
                .authorName("Mohammad Akbari")
                .build();
    }

    @Test
    void shouldCreateNewWorkLogForTask_whenAuthorAndTaskBothExist() throws Exception {
        CreateWorkLogRequest request = CreateWorkLogRequest.builder()
                .description("Jwt authentication was implemented")
                .build();
        given(service.createWorkLog(anyLong(), anyLong(), any(CreateWorkLogRequest.class)))
                .willReturn(summary);

        mockMvc.perform(post("/work-log/task/2")
                .with(user(currentUser))
                .with(csrf())
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))

                .andExpect(status().isCreated())
                .andExpect(jsonPath("data.['task title']").value("Implementation Login Flow"))
                .andExpect(jsonPath("$.data.description").value("Jwt authentication was implemented"))
                .andExpect(jsonPath("$.data.['author name']").value("Mohammad Akbari"));
    }
}
