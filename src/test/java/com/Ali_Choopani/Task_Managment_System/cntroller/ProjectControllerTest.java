package com.Ali_Choopani.Task_Managment_System.cntroller;

import com.Ali_Choopani.Task_Managment_System.controllers.ProjectController;
import com.Ali_Choopani.Task_Managment_System.dto.project.CreateProjectRequest;
import com.Ali_Choopani.Task_Managment_System.dto.project.ProjectMemberSummary;
import com.Ali_Choopani.Task_Managment_System.entities.UserRole;
import com.Ali_Choopani.Task_Managment_System.security.CustomUserDetailsService;
import com.Ali_Choopani.Task_Managment_System.security.JwtService;
import com.Ali_Choopani.Task_Managment_System.security.UserDetailImpl;
import com.Ali_Choopani.Task_Managment_System.services.ProjectService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;

import static com.Ali_Choopani.Task_Managment_System.entities.UserRole.ROLE_USER;
import static java.time.LocalDate.of;
import static java.util.Collections.singleton;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ProjectController.class)
public class ProjectControllerTest {

    @MockitoBean
    private JwtService jwtService;
    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @MockitoBean
    private ProjectService service;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    private ProjectMemberSummary projectSummary;
    private UserDetailImpl userDetail;

    @BeforeEach
    void setUp() {
        projectSummary = ProjectMemberSummary.builder()
                .id(1L)
                .title("Design A Formal Logo")
                .description("Design a formal logo for a transportation company")
                .startDate(of(20026,2,20))
                .dueDate(of(2026,5,10))
                .managerName("Ali Ahmadi")
                .managerId(2L)
                .build();
        userDetail = UserDetailImpl.builder()
                .id(2L)
                .username("Ali_Ahmadi3567@gmail.com")
                .authorities(singleton(new SimpleGrantedAuthority(ROLE_USER.name())))
                .build();
    }

    @Test
    void shouldCreateANewProject() throws Exception {
        CreateProjectRequest request = CreateProjectRequest.builder()
                .title("Design A Formal Logo")
                .description("Design a formal logo for a transportation company")
                .dueDate(of(2026,12,30))
                .build();

        given(service.createAProject(any(CreateProjectRequest.class) ,anyLong()))
                .willReturn(projectSummary);

        mockMvc.perform(post("/project")
                .with(user(userDetail))
                .with(csrf())
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(jsonPath("$.data.title").value("Design A Formal Logo"))
                .andExpect(jsonPath("$.data.['manager name']").value("Ali Ahmadi"));
    }
}
