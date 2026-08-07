package com.Ali_Choopani.Task_Managment_System.mappers;

import com.Ali_Choopani.Task_Managment_System.dto.ProjectMemberSummary;
import com.Ali_Choopani.Task_Managment_System.entities.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

import static com.Ali_Choopani.Task_Managment_System.entities.ProjectRole.ROLE_DEVELOPER;
import static com.Ali_Choopani.Task_Managment_System.entities.UserRole.ROLE_USER;
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
                .name("Ali")
                .lastName("Choopani")
                .build();
        profile.addProfileToUser(member);

        Project project = Project.builder()
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
        final ProjectMemberSummary summary = mapper.toSummary(projectMember);

        assertThat(summary)
                .extracting(ProjectMemberSummary::title, ProjectMemberSummary::startDate,ProjectMemberSummary::managerName)
                .containsExactly("Implementation the backend of a business site", of(2026,2,10),"Ali Choopani");
        }
    }
