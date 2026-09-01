package com.Ali_Choopani.Task_Management_System.repository;

import com.Ali_Choopani.Task_Management_System.dto.task.MyTasksSummary;
import com.Ali_Choopani.Task_Management_System.entities.*;
import com.Ali_Choopani.Task_Management_System.repositories.ProjectMemberRepository;
import com.Ali_Choopani.Task_Management_System.repositories.TaskRepository;
import com.Ali_Choopani.Task_Management_System.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.Ali_Choopani.Task_Management_System.entities.ProjectRole.ROLE_DEVELOPER;
import static com.Ali_Choopani.Task_Management_System.entities.TaskStatus.COMPLETED;
import static com.Ali_Choopani.Task_Management_System.entities.TaskStatus.IN_PROGRESS;
import static com.Ali_Choopani.Task_Management_System.testFactories.ProjectMemberTestFactory.createProjectMember;
import static com.Ali_Choopani.Task_Management_System.testFactories.ProjectTestFactory.createProject;
import static com.Ali_Choopani.Task_Management_System.testFactories.TaskTestFactory.createTask;
import static com.Ali_Choopani.Task_Management_System.testFactories.UserTestFactory.createUser;
import static java.time.LocalDate.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.springframework.data.domain.Sort.by;

@DataJpaTest
@ActiveProfiles("test")
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository repository;
    @Autowired
    private ProjectMemberRepository projectMemberRepository;
    @Autowired
    private UserRepository userRepository;

    private User savedUser;
    private ProjectMember projectMember;

    @BeforeEach
    void setUp() {
        Project project = createProject(null, "Task Management System", null, of(2026, 12,25));
        User member = createUser(null, "09876543210", "Ch123@76", "Akbar Hasani");
       savedUser = userRepository.save(member);

        ProjectMember projectMember = createProjectMember(null, member, ROLE_DEVELOPER, project);
        projectMember.addProjectMember(member, project);

        Task firstTask = createTask(null, "Implementation the authentication section",
                null, of(2026, 9, 30), IN_PROGRESS,project, projectMember);
        Task secondTask = createTask(null, "Adding user search by email or full name feature",
                null, of(2026, 12, 20), COMPLETED,project, projectMember);

        List.of(firstTask, secondTask)
                .forEach(t -> t.assignTaskToMember(projectMember));

        this.projectMember = projectMemberRepository.save(projectMember);
        repository.saveAll(List.of(firstTask, secondTask));
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

    @Test
    void shouldFindByUserId_whenExists() {
        final Page<MyTasksSummary> userTasks = repository.findByUserIdAndReturnTasksSummary(savedUser.getId(),
                PageRequest.of(0, 10, by("title")));

        assertThat(userTasks)
                .extracting(MyTasksSummary::title, MyTasksSummary::status)
                .containsExactly(tuple("Adding user search by email or full name feature", COMPLETED),
                        tuple("Implementation the authentication section", IN_PROGRESS));
    }
}
