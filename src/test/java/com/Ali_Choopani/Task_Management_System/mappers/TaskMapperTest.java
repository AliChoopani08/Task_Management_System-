package com.Ali_Choopani.Task_Management_System.mappers;

import com.Ali_Choopani.Task_Management_System.dto.task.TaskSummary;
import com.Ali_Choopani.Task_Management_System.entities.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static com.Ali_Choopani.Task_Management_System.entities.ProjectRole.ROLE_DEVELOPER;
import static com.Ali_Choopani.Task_Management_System.entities.ProjectRole.ROLE_MANAGER;
import static com.Ali_Choopani.Task_Management_System.entities.TaskStatus.IN_PROGRESS;
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
    private ProjectMember projectDeveloper;

    @BeforeEach
    void setUp() {
        Project project = Project.builder()
                .title("Implementation A Business WebSite")
                .id(1L)
                .dueDate(of(2026,10,10))
                .build();

        projectManager = createProjectMember("09876543210", "Zahra Akbari", ROLE_MANAGER, project);
        projectDeveloper = createProjectMember("091234567890", "Ali Ahmadi", ROLE_DEVELOPER, project);

        task = Task.builder()
                .title("Implementation The Authentication Flow")
                .description("Implementation the flow of login and registration")
                .createAt(of(2026,8,20))
                .dueDate(of(2026,12,30))
                .status(IN_PROGRESS)
                .build();

        task.addTaskProject(project);
        task.assignTaskToMember(projectDeveloper);
    }

    @Test
    void shouldMapEntityToSummary() {
        final TaskSummary summary = taskMapper.toSummary(task, projectManager);

        assertThat(summary)
                .extracting(TaskSummary::title, t -> t.project().title(), t-> t.assignee().fullName(),
                        t -> t.project().manager().name())
                .containsExactly(task.getTitle(), task.getProject().getTitle(), "Ali Ahmadi", "Zahra Akbari");
    }

    private ProjectMember createProjectMember(String phoneNumber, String fullName, ProjectRole projectRole, Project project) {
        final String[] separatedFullName = fullName.split(" ");

        final User user = User.builder()
                .phoneNumber(phoneNumber)
                .build();
        Profile profile = Profile.builder()
                .firstName(separatedFullName[0])
                .surname(separatedFullName[1])
                .build();
        profile.addProfileToUser(user);
        ProjectMember projectMember = ProjectMember.builder()
                .role(projectRole)
                .build();
        projectMember.addProjectMember(user, project);

        return projectMember;
    }
}
