package com.Ali_Choopani.Task_Management_System.service;

import com.Ali_Choopani.Task_Management_System.dto.workLog.CreateWorkLogRequest;
import com.Ali_Choopani.Task_Management_System.dto.workLog.WorkLogDetails;
import com.Ali_Choopani.Task_Management_System.entities.*;
import com.Ali_Choopani.Task_Management_System.mappers.WorkLogMapper;
import com.Ali_Choopani.Task_Management_System.repositories.WorkLogRepository;
import com.Ali_Choopani.Task_Management_System.repositories.TaskRepository;
import com.Ali_Choopani.Task_Management_System.services.workLog.WorkLogServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.Ali_Choopani.Task_Management_System.TestMocksHelper.MockWhenHelper.whenHelper;
import static com.Ali_Choopani.Task_Management_System.entities.ProjectRole.ROLE_DEVELOPER;
import static com.Ali_Choopani.Task_Management_System.entities.TaskStatus.IN_PROGRESS;
import static com.Ali_Choopani.Task_Management_System.testFactories.CommentTestFactory.createComment;
import static com.Ali_Choopani.Task_Management_System.testFactories.ProjectMemberTestFactory.createProjectMember;
import static com.Ali_Choopani.Task_Management_System.testFactories.ProjectTestFactory.createProject;
import static com.Ali_Choopani.Task_Management_System.testFactories.TaskTestFactory.createTask;
import static com.Ali_Choopani.Task_Management_System.testFactories.UserTestFactory.createUser;
import static java.time.LocalDate.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    private WorkLogRepository repository;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private WorkLogMapper mapper;

    @InjectMocks
    private WorkLogServiceImpl service;

    private User user;
    private Task task;
    private WorkLog comment;
    private WorkLogDetails summary;

    @BeforeEach
    void setUp() {
        final Project project = createProject(1L, "Financial Management System", null, of(2027, 5, 10));
        user = createUser(2L, "09876543211", null, "Maryam Asadi");
        final ProjectMember projectMember = createProjectMember(3L, user, ROLE_DEVELOPER, project);
        task = createTask(4L, "Implementation Login Feature", null, of(2026, 11, 30), IN_PROGRESS,
                project, projectMember);
        comment = createComment(5L, "The business logic of login flow was wrote successfully", projectMember, task);
        summary = WorkLogDetails.builder()
                .description("The business logic of login flow was wrote successfully")
                .authorId(2L)
                .taskId(4L)
                .build();
    }

    @Test
    void shouldCreateNewCommit_whenTaskAndAuthorBothExist() {
        final CreateWorkLogRequest request = CreateWorkLogRequest.builder()
                .description("The business logic of login flow was wrote successfully")
                .build();

        whenHelper(taskRepository.findByIdAndUserId(anyLong(), anyLong()), Optional.of(task));
        whenHelper(mapper.toEntity(any(CreateWorkLogRequest.class)), comment);
        whenHelper(repository.save(any(WorkLog.class)), comment);
        whenHelper(mapper.toDetails(any(WorkLog.class)), summary);

        final WorkLogDetails serviceResponse = service.createWorkLog(user.getId(), task.getId(), request);

        assertThat(serviceResponse)
                .extracting(WorkLogDetails::description, WorkLogDetails::authorId, WorkLogDetails::taskId)
                .containsExactly("The business logic of login flow was wrote successfully", user.getId(), task.getId());
    }
}
