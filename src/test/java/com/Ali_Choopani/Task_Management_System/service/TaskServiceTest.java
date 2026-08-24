package com.Ali_Choopani.Task_Management_System.service;

import com.Ali_Choopani.Task_Management_System.dto.project.ProjectSummary;
import com.Ali_Choopani.Task_Management_System.dto.task.CreateTaskRequest;
import com.Ali_Choopani.Task_Management_System.dto.task.TaskSummary;
import com.Ali_Choopani.Task_Management_System.entities.*;
import com.Ali_Choopani.Task_Management_System.mappers.TaskMapper;
import com.Ali_Choopani.Task_Management_System.repositories.ProjectMemberRepository;
import com.Ali_Choopani.Task_Management_System.repositories.TaskRepository;
import com.Ali_Choopani.Task_Management_System.services.task.TaskServiceImpl;
import com.Ali_Choopani.Task_Management_System.testFactories.ProjectMemberTestFactory;
import com.Ali_Choopani.Task_Management_System.testFactories.ProjectTestFactory;
import com.Ali_Choopani.Task_Management_System.testFactories.TaskTestFactory;
import com.Ali_Choopani.Task_Management_System.testFactories.UserTestFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.Ali_Choopani.Task_Management_System.TestMocksHelper.MockWhenHelper.whenHelper;
import static com.Ali_Choopani.Task_Management_System.entities.ProjectRole.ROLE_DEVELOPER;
import static com.Ali_Choopani.Task_Management_System.entities.ProjectRole.ROLE_MANAGER;
import static com.Ali_Choopani.Task_Management_System.entities.TaskStatus.TODO;
import static com.Ali_Choopani.Task_Management_System.testFactories.ProjectMemberTestFactory.createProjectSummary;
import static java.time.LocalDate.of;
import static java.util.Optional.empty;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    
    @Mock
    private TaskRepository repository;
    @Mock
    private ProjectMemberRepository projectMemberRepository;
    @Mock
    private TaskMapper mapper;

    @InjectMocks
    private TaskServiceImpl service;

    private Project project;
    private Task task;
    private TaskSummary summary;
    private ProjectMember projectManager;
    private ProjectMember projectDeveloper;

    @BeforeEach
    void setUp() {
        project = ProjectTestFactory.createProject(1L,"Set Up A Sporting Web Site",
                "Implementation a sporting web site to share the sport news", of(2027,1,30));

        final User user1 = UserTestFactory.createUser(2L, "09876543210", null, "Maryam Hosseini");
        final User user2 = UserTestFactory.createUser(3L,"reza_salami.123@gmail.com", null, "Reza Salami");

        projectManager = ProjectMemberTestFactory.createProjectMember(project, user1, ROLE_MANAGER);
        projectDeveloper = ProjectMemberTestFactory.createProjectMember(project, user2, ROLE_DEVELOPER);

        ProjectSummary projectSummary = createProjectSummary(projectManager);

        task = TaskTestFactory.createTask("Implementation authentication flow", "Implementation login and registration flow",
                of(2027,1,30), TODO, project, null);

        summary = TaskTestFactory.createTaskSummary(task, projectSummary);
    }

    @Test
    void shouldCreateANewTask_whenProjectManagerAndProjectExist() {
        CreateTaskRequest request = CreateTaskRequest.builder()
                .title("Implementation Backend Section")
                .description("""
                        Implementation the backend section 
                        include authentication, the flow of adding newa to web site and writing efficient queries""")
                .dueDate(of(2027,1,30))
                .build();
        final Long projectId = project.getId();
        final Long managerId = projectManager.getMember().getId();

        whenHelper(projectMemberRepository.findByProjectIdAndMemberIdAndRole(anyLong(), anyLong(), any(ProjectRole.class)),
                Optional.of(projectManager));
        whenHelper(repository.findByProjectIdAndTitleIgnoreCase(anyLong(), anyString()), empty());
        whenHelper(mapper.toEntity(any(CreateTaskRequest.class)), task);
        whenHelper(repository.save(any(Task.class)), task);
        whenHelper(mapper.toSummary(any(Task.class), any(ProjectMember.class)), summary);

        final TaskSummary responseMethod = service.createANewTaskOfProject(projectId, managerId, request);

        Assertions.assertThat(responseMethod)
                .extracting(TaskSummary::title, t -> t.project().title(), t -> t.project().manager().name())
                .containsExactly(task.getTitle(), project.getTitle(), "Maryam Hosseini");
    }
}
