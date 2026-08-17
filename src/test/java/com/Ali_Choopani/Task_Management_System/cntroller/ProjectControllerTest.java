package com.Ali_Choopani.Task_Management_System.cntroller;

import com.Ali_Choopani.Task_Management_System.controllers.ProjectController;
import com.Ali_Choopani.Task_Management_System.dto.project.*;
import com.Ali_Choopani.Task_Management_System.entities.User;
import com.Ali_Choopani.Task_Management_System.repositories.ProjectMemberRepository;
import com.Ali_Choopani.Task_Management_System.security.securityExceptionHandlers.AuthenticationEntryPoint;
import com.Ali_Choopani.Task_Management_System.security.CustomUserDetailsService;
import com.Ali_Choopani.Task_Management_System.security.JwtService;
import com.Ali_Choopani.Task_Management_System.security.UserDetailImpl;
import com.Ali_Choopani.Task_Management_System.security.customeAuthorization.ProjectAuthorization;
import com.Ali_Choopani.Task_Management_System.services.ProjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static com.Ali_Choopani.Task_Management_System.entities.ProjectRole.ROLE_DEVELOPER;
import static com.Ali_Choopani.Task_Management_System.entities.ProjectRole.ROLE_MANAGER;
import static com.Ali_Choopani.Task_Management_System.entities.UserRole.ROLE_USER;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProjectController.class)
public class ProjectControllerTest {

    @MockitoBean
    private JwtService jwtService;
    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;
    @MockitoBean
    private ProjectService service;
    @MockitoBean
    private ProjectMemberRepository repository;
    @MockitoBean
    private ProjectAuthorization projectAuthorization;
    @MockitoBean
    private AuthenticationEntryPoint authEntryPoint;


    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    private ProjectSummary projectSummary;
    private User user;
    private UserDetailImpl userDetail;

    @BeforeEach
    void setUp() {
        projectSummary = ProjectSummary.builder()
                .id(1L)
                .title("Design A Formal Logo")
                .description("Design a formal logo for a transportation company")
                .startDate(of(20026,2,20))
                .dueDate(of(2026,5,10))
                .manager(new MemberSummary(2L, "Ali Ahmadi", ROLE_MANAGER))
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

    @Test
    void shouldAddNewProjectMember_whenManagerAndProjectExistAndManagerHasLoggedIn() throws Exception {
        AddNewProjectMemberRequest request = AddNewProjectMemberRequest.builder()
                .memberRole(ROLE_DEVELOPER.name())
                .build();
        ProjectMemberSummary projectMemberSummary = ProjectMemberSummary.builder()
                .project(projectSummary)
                .members(Set.of(new MemberSummary(3L, "Akbar Hoseyni", ROLE_DEVELOPER)))
                .build();

        given(projectAuthorization.isManager(any(Authentication.class), anyLong()))
                .willReturn(true);
        given(service.addProjecetMember(anyLong(), anyLong(), anyLong(), any(AddNewProjectMemberRequest.class)))
                .willReturn(projectMemberSummary);

        mockMvc.perform(post("/project/1/member/3")
                .with(user(userDetail))
                .with(csrf())
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.project.['project id']").value(1))
                .andExpect(jsonPath("$.data.members[0].name").value("Akbar Hoseyni"));
    }
}
