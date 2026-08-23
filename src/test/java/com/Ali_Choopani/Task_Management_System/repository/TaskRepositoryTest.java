package com.Ali_Choopani.Task_Management_System.repository;

import com.Ali_Choopani.Task_Management_System.entities.*;
import com.Ali_Choopani.Task_Management_System.repositories.ProjectMemberRepository;
import com.Ali_Choopani.Task_Management_System.repositories.TaskRepository;
import com.Ali_Choopani.Task_Management_System.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static com.Ali_Choopani.Task_Management_System.entities.ProjectRole.ROLE_DEVELOPER;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository repository;
    @Autowired
    private ProjectMemberRepository projectMemberRepository;
    @Autowired
    private UserRepository userRepository;

    private ProjectMember projectMember;

    @BeforeEach
    void setUp() {
        Project project = Project.builder()
                .title("Implementation A Task Management Systems Application")
                .description("Implementation a task management systems application to manage the duties of members at developing the app")
                .dueDate(LocalDate.of(2026, 12,25))
                .build();
        User member = User.builder()
                .phoneNumber("09876543210")
                .password("Ch123@76")
                .build();
        userRepository.save(member);
        ProjectMember projectMember = ProjectMember.builder()
                .role(ROLE_DEVELOPER)
                .build();
        projectMember.addProjectMember(member, project);

        Task task = Task.builder()
                .title("Developing the authentication section")
                .description("Improve the performance of authentication with sending code to user's mail box while registration or login")
                .dueDate(LocalDate.of(2026, 9, 30))
                .build();
        task.assignTaskToMember(projectMember);

        this.projectMember = projectMemberRepository.save(projectMember);
    }

    @Test
    @Transactional(readOnly = true)
    void shouldFindTaskInProjectByTitle_whenExists() {
        String taskTitle = projectMember.getTasks().iterator().next().getTitle();
        final Long projectMemberId = projectMember.getId();
        final String projectTitle = projectMember.getProject().getTitle();

        final Optional<Task> foundTask = repository.findByAssigneeIdAndTitleIgnoreCase(projectMemberId, taskTitle);

        assertThat(foundTask.isPresent()).isTrue();
        foundTask.ifPresent(task -> assertThat(task)
                .extracting(Task::getTitle, t -> t.getAssignee().getProject().getTitle())
                .containsExactly(taskTitle, projectTitle));
    }
}
