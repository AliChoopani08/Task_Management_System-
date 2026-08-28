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
import com.Ali_Choopani.Task_Management_System.services.project.ProjectService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        userDetail = UserDetailImpl.builder()
                .id(1L)
                .username("Ali_Ahmadi3567@gmail.com")
                .authorities(singleton(new SimpleGrantedAuthority(ROLE_USER.name())))
                .build();
        projectSummary = ProjectSummary.builder()
                .id(2L)
                .title("Design A Formal Logo")
                .manager(new MemberSummary(1L, "Ali Ahmadi", ROLE_MANAGER))
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
        ProjectDetails projectMemberSummary = ProjectDetails.builder()
                .build();

        given(projectAuthorization.isManager(any(Authentication.class)))
                .willReturn(true);
        given(service.addProjectMember(anyLong(), anyLong(), anyLong(), any(AddNewProjectMemberRequest.class)))
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

    @Test
    void shouldReturnProjectsSummaryOfUser_whenUserHasLoggedIn() throws Exception {
        final Long userId = userDetail.getId();
        Set<MyProjectsSummary> exceptedMyProjectsSummary = Set.of(new MyProjectsSummary(2L, "Design A Formal Logo", ROLE_MANAGER));

        given(service.getMyProjectsSummary(anyLong()))
                .willReturn(exceptedMyProjectsSummary);

        mockMvc.perform(get("/project/my/projects-summary")
                .with(user(userDetail))
                .with(csrf()))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].title").value("Design A Formal Logo"))
                .andExpect(jsonPath("$.data[0].role").value("ROLE_MANAGER"));
    }
}
