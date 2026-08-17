package com.Ali_Choopani.Task_Management_System.mappers;

import com.Ali_Choopani.Task_Management_System.dto.project.ProjectSummary;
import com.Ali_Choopani.Task_Management_System.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static com.Ali_Choopani.Task_Management_System.entities.ProjectRole.ROLE_DEVELOPER;
import static com.Ali_Choopani.Task_Management_System.entities.UserRole.ROLE_USER;
import static java.time.LocalDate.of;
import static org.assertj.core.api.Assertions.assertThat;

public class ProjectMemberMapperTest {

    private final ProjectMemberMapper mapper = Mappers.getMapper(ProjectMemberMapper.class);

    private ProjectMember projectMember;

    @BeforeEach
    void setUp() {
        User member = User.builder()
                .id(2L)
                .email("Ali_ch_1234@gami.com")
                .role(ROLE_USER)
                .password("Ali@12345")
                .build();
        Profile profile = Profile.builder()
                .firstName("Ali")
                .surname("Choopani")
                .build();
        profile.addProfileToUser(member);

        Project project = Project.builder()
                .id(3L)
                .title("Implementation the backend of a business site")
                .description("Writing the initial business logic of site")
                .startDate(of(2026,2,10))
                .dueDate(of(2026,8,30))
                .build();

        projectMember = ProjectMember.builder()
                .role(ROLE_DEVELOPER)
                .build();

        projectMember.addProjectMember(member, project);
    }

    @Test
    void shouldMapEntityToSummary() {
        final ProjectSummary summary = mapper.toSummary(projectMember);
        System.out.println(summary);

        assertThat(summary)
                .extracting(ProjectSummary::title, ProjectSummary::startDate, p -> p.manager().name())
                .containsExactly("Implementation the backend of a business site", of(2026,2,10),"Ali Choopani");
        }
    }
