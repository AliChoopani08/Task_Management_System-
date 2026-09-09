package com.Ali_Choopani.Task_Management_System.mappers;

import com.Ali_Choopani.Task_Management_System.dto.task.ProjectTasksSummary;
import com.Ali_Choopani.Task_Management_System.dto.task.TaskDetails;
import com.Ali_Choopani.Task_Management_System.entities.*;
import com.Ali_Choopani.Task_Management_System.testFactories.ProjectMemberTestFactory;
import com.Ali_Choopani.Task_Management_System.testFactories.ProjectTestFactory;
import com.Ali_Choopani.Task_Management_System.testFactories.TaskTestFactory;
import com.Ali_Choopani.Task_Management_System.testFactories.UserTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.Ali_Choopani.Task_Management_System.entities.ProjectRole.ROLE_DEVELOPER;
import static com.Ali_Choopani.Task_Management_System.entities.ProjectRole.ROLE_MANAGER;
import static com.Ali_Choopani.Task_Management_System.entities.TaskStatus.IN_PROGRESS;
import static com.Ali_Choopani.Task_Management_System.testFactories.ProjectMemberTestFactory.createProjectMember;
import static com.Ali_Choopani.Task_Management_System.testFactories.ProjectTestFactory.createProject;
import static com.Ali_Choopani.Task_Management_System.testFactories.TaskTestFactory.createTask;
import static com.Ali_Choopani.Task_Management_System.testFactories.UserTestFactory.createUser;
import static java.time.LocalDate.of;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TaskMapperTest {

    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private ProjectMemberMapper projectMemberMapper;

    private Task task;
    private ProjectMember projectManager;

    @BeforeEach
    void setUp() {
        final Project project = createProject(1L, "Banking System", "Bank accounts management", of(2026, 10, 10));
        final User user1 = createUser(2L, "09876543210", null, "Zahra Akbari");
        final User user2 = createUser(3L, "091234567890", null, "Ali Ahmadi");

        projectManager = createProjectMember(4L, user1, ROLE_MANAGER, project);
        ProjectMember projectDeveloper = createProjectMember(5L, user2, ROLE_DEVELOPER, project);

        this.task = createTask(null, "Implementation The Authentication Flow",
                "Implementation the flow of login and registration", of(2026, 12, 30),
                IN_PROGRESS, project, projectDeveloper);

        this.task.addTaskProject(project);
        this.task.assignTaskToMember(projectDeveloper);
    }

    @Test
    void shouldMapEntityToSummary() {
        final TaskDetails summary = taskMapper.toSummary(task, projectManager);

        assertThat(summary)
                .extracting(TaskDetails::title, t -> t.project().title(), t -> t.assignee().fullName(),
                        t -> t.project().manager().name())
                .containsExactly(task.getTitle(), task.getProject().getTitle(), "Ali Ahmadi", "Zahra Akbari");
    }

    @Test
    void shouldMapToProjectTaskSummary() {
        final ProjectTasksSummary projectTaskSummary = taskMapper.toProjectTaskSummary(task);

        assertThat(projectTaskSummary)
                .extracting(ProjectTasksSummary::taskTitle, ProjectTasksSummary::projectTitle, ProjectTasksSummary::assigneeFullName)
                .containsExactly("Implementation The Authentication Flow", "Banking System", "Ali Ahmadi");
    }
}
