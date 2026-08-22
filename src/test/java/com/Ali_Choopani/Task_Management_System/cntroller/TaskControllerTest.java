package com.Ali_Choopani.Task_Management_System.cntroller;

import com.Ali_Choopani.Task_Management_System.controllers.TaskController;
import com.Ali_Choopani.Task_Management_System.dto.project.CreateProjectRequest;
import com.Ali_Choopani.Task_Management_System.dto.project.MemberSummary;
import com.Ali_Choopani.Task_Management_System.dto.project.ProjectSummary;
import com.Ali_Choopani.Task_Management_System.dto.task.AssigneeSummary;
import com.Ali_Choopani.Task_Management_System.dto.task.CreateTaskRequest;
import com.Ali_Choopani.Task_Management_System.dto.task.TaskSummary;
import com.Ali_Choopani.Task_Management_System.entities.ProjectRole;
import com.Ali_Choopani.Task_Management_System.entities.UserRole;
import com.Ali_Choopani.Task_Management_System.security.CustomUserDetailsService;
import com.Ali_Choopani.Task_Management_System.security.JwtService;
import com.Ali_Choopani.Task_Management_System.security.UserDetailImpl;
import com.Ali_Choopani.Task_Management_System.security.securityExceptionHandlers.AuthenticationEntryPoint;
import com.Ali_Choopani.Task_Management_System.services.task.TaskService;
import com.Ali_Choopani.Task_Management_System.testFactories.TaskTestFactory;
import com.Ali_Choopani.Task_Management_System.testFactories.UserTestFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static com.Ali_Choopani.Task_Management_System.entities.ProjectRole.ROLE_MANAGER;
import static com.Ali_Choopani.Task_Management_System.entities.UserRole.ROLE_USER;
import static java.time.LocalDate.of;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @MockitoBean
    private TaskService service;
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

    private UserDetailImpl loggedInUser;
    private TaskSummary taskSummary;
    private ProjectSummary projectSummary;

    @BeforeEach
    void setUp() {
        loggedInUser = UserTestFactory.createUserDetail(1L, "09876543211", ROLE_USER);
         projectSummary = ProjectSummary.builder()
                .id(2L)
                .title("Implementation A Store App")
                .manager(new MemberSummary(1L,"Ali Ahmadi", ROLE_MANAGER))
                .build();
        taskSummary = TaskSummary.builder()
                .id(3L)
                .title("Implementation The Entities Of A Store App")
                .dueDate(of(2026,10,20))
                .project(projectSummary)
                .build();
    }

    @Test
    void shouldCreateATask_whenBothProjectAndManagerExist() throws Exception {
        CreateTaskRequest request = CreateTaskRequest.builder()
                .title("Implementation The Entities Of A Store App")
                .description("lllllllllllllllllllllllllllllllllllllllllllll")
                .dueDate(of(2026,10,20))
                .build();

        given(service.createANewTaskOfProject(anyLong(), anyLong(), any(CreateTaskRequest.class)))
                .willReturn(taskSummary);

        mockMvc.perform(post("/task/project/2")
                .with(user(loggedInUser))
                .with(csrf())
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.title").value(taskSummary.title()))
                .andExpect(jsonPath("$.data.project.title").value(projectSummary.title()));
    }
}
