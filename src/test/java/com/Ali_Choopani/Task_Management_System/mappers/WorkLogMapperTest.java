package com.Ali_Choopani.Task_Management_System.mappers;

import com.Ali_Choopani.Task_Management_System.dto.workLog.WorkLogDetails;
import com.Ali_Choopani.Task_Management_System.entities.*;
import com.Ali_Choopani.Task_Management_System.testFactories.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.Ali_Choopani.Task_Management_System.entities.ProjectRole.ROLE_DEVELOPER;
import static com.Ali_Choopani.Task_Management_System.entities.TaskStatus.IN_PROGRESS;
import static com.Ali_Choopani.Task_Management_System.testFactories.ProjectMemberTestFactory.createProjectMember;
import static com.Ali_Choopani.Task_Management_System.testFactories.ProjectTestFactory.createProject;
import static com.Ali_Choopani.Task_Management_System.testFactories.TaskTestFactory.createTask;
import static com.Ali_Choopani.Task_Management_System.testFactories.WorkLogTestFactory.createWorkLog;
import static java.time.LocalDate.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mapstruct.factory.Mappers.getMapper;

public class WorkLogMapperTest {

    private final WorkLogMapper mapper = getMapper(WorkLogMapper.class);

    private WorkLog workLog;

    @BeforeEach
    void setUp() {
        final User user = UserTestFactory.createUser(1L, "aliAsadi_123@gmail.com", null, "Ali Asadi");
        final Project project = createProject(2L, "Store App", null, of(2027, 1, 30));
        final ProjectMember projectMember = createProjectMember(3L, user, ROLE_DEVELOPER, project);
        final Task task = createTask(4L, "Implementation Add Product To Stock", null, of(2026, 12, 30),
                IN_PROGRESS, project, projectMember);
        workLog = createWorkLog(5L, "The service of adding product to stock was implemented", projectMember, task);
    }

    @Test
    void shouldMapToDetails() {
        final WorkLogDetails workLogDetails = mapper.toDetails(workLog);

        assertThat(workLogDetails)
              .extracting(WorkLogDetails::authorName, WorkLogDetails::taskTitle, WorkLogDetails::description)
              .containsExactly("Ali Asadi", "Implementation Add Product To Stock", "The service of adding product to stock was implemented");
    }
}
